package it.fedet.mutility.common.server.text;

public class TextPlaceholder {

    private final String placeholder;
    private final String value;

    public TextPlaceholder(String placeholder, String value) {
        this.placeholder = placeholder;
        this.value = value;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getValue() {
        return value;
    }

}