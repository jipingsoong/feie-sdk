package com.eeeyou.feiesdk.bulid;

public class FeiEYunPrintBuilder {

    private final StringBuilder content = new StringBuilder();

    public FeiEYunPrintBuilder text(int x, int y, int font, int w, int h, int r, String text) {
        content.append(String.format("<TEXT x=\"%d\" y=\"%d\" font=\"%d\" w=\"%d\" h=\"%d\" r=\"%d\">%s</TEXT>", 
                x, y, font, w, h, r, escape(text)));
        return this;
    }

    public FeiEYunPrintBuilder qr(int x, int y, String e, int w, String value) {
        content.append(String.format("<QR x=\"%d\" y=\"%d\" e=\"%s\" w=\"%d\">%s</QR>", 
                x, y, e, w, escape(value)));
        return this;
    }

    public FeiEYunPrintBuilder size(int width, int height) {
        content.append(String.format("<SIZE>%d,%d</SIZE>", width, height));
        return this;
    }

    public FeiEYunPrintBuilder direction(int n) {
        content.append(String.format("<DIRECTION>%d</DIRECTION>", n));
        return this;
    }

    public String build() {
        return content.toString();
    }

    private String escape(String text) {
        return text.replace("<", "&lt;").replace(">", "&gt;");
    }
}
