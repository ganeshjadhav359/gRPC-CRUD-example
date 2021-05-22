package com.lms.exceptions;

import io.grpc.*;

import java.util.logging.Logger;

public class ExceptionHandler implements ServerInterceptor {

    public static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    @Override
    public <ReqT, RespT>  ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                         Metadata metadata,
                                                                         ServerCallHandler<ReqT, RespT> serverCallHandler) {
        ServerCall.Listener<ReqT> listener =  serverCallHandler.startCall(serverCall,metadata);

        return new ExceptionHandlerServerCallListener<>(listener, serverCall, metadata);
    }

    private class ExceptionHandlerServerCallListener<ReqT, RespT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {

        private final ServerCall<ReqT,RespT> serverCall;
        private final Metadata metadata;
        protected ExceptionHandlerServerCallListener(ServerCall.Listener<ReqT> delegate,ServerCall<ReqT, RespT> serverCall,
                                                    Metadata metadata) {
            super(delegate);
            this.serverCall=serverCall;
            this.metadata=metadata;
        }

        @Override
        public void onHalfClose() {
            ExceptionHandler.logger.info("on half close method called in handler listener");
            try{
                super.onHalfClose();
            }catch (RuntimeException exception){
                handleException(exception);
                throw exception;
            }
        }

        @Override
        public void onCancel() {

            ExceptionHandler.logger.info("on cancel method called in handler listener");

            try{
                super.onCancel();
            }catch (RuntimeException exception){
                handleException(exception);
                throw exception;
            }
        }

        @Override
        public void onComplete() {


            ExceptionHandler.logger.info("on complete method called in handler listener");

            try{
                super.onComplete();
            }catch (RuntimeException exception){
                handleException(exception);
                throw exception;
            }
        }

        @Override
        public void onReady() {

            ExceptionHandler.logger.info("on ready method called in handler listener");

            try{
                super.onReady();
            }catch (RuntimeException exception){
                handleException(exception);
                throw exception;
            }
        }


        private void handleException(RuntimeException exception){
            if(exception instanceof IllegalArgumentException){
                this.serverCall.close(Status.INVALID_ARGUMENT.withDescription(exception.getMessage()),this.metadata);
            }
            else{
                this.serverCall.close(Status.INTERNAL.withDescription(exception.getMessage()),this.metadata);
            }
        }
    }
}
