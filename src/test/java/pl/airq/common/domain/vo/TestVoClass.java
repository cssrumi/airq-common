package pl.airq.common.domain.vo;

class TestVoClass<T> {

    public T field;

    static String getRaw(String fieldValue) {
        return "{\"field\":" + fieldValue + "}";
    }

}
