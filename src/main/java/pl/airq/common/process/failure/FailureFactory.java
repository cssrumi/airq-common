package pl.airq.common.process.failure;

public class FailureFactory {

    private FailureFactory() {
    }

    public static Failure fromThrowable(Throwable throwable) {
        return new Failure(new FailurePayload(throwable));
    }
}
