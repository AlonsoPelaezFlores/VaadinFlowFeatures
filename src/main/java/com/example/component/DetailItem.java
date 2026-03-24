package com.example.component;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

@CssImport("./themes/principal/component/detail-item.css")
public class DetailItem extends Div {
    public DetailItem(String label, String value) {
        addClassName("detail-item");

        Span labelSpan = new Span(label);
        labelSpan.addClassName("detail-label");

        Span valueSpan = new Span(value);
        valueSpan.addClassName("detail-value");

        add(labelSpan, valueSpan);
    }
}
