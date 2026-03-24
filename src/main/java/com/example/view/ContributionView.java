package com.example.view;

import com.example.backend.dto.FilerData;
import com.example.backend.dto.FilterRangeDTO;
import com.example.component.ContributionReportsGrid;
import com.example.component.MonthYearFilter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("")
@PageTitle("Contribution")
public class ContributionView extends VerticalLayout {
    public ContributionView(){
        H1 title = new H1("Declaracio de Cotizacio");
        add(title,filerData(),showContributionReports());
    }
    private Component filerData() {
        FilerData filer = new FilerData();
        H3 title = new H3("Dades del declarant");
        FormLayout formData = new FormLayout();
        formData.addFormItem(new Span("Numero del declarant:"), filer.getFilerNumber());
        formData.addFormItem(new Span("Nom del declarant:"),filer.getFilerName());
        formData.addFormItem( new Span("NRT:"),filer.getNrtNumber());
        formData.addFormItem(new Span("0.0"), new Span("Saldo cotizacions:"));

        Div divData = new Div(title,formData);
        divData.addClassName("card");

        Div divButton =new Div(buttonsAction());
        HorizontalLayout cardsContainer = new HorizontalLayout();
        cardsContainer.add(divData,divButton);
        cardsContainer.setFlexGrow(3,divData);
        cardsContainer.setFlexGrow(1,divButton);
        cardsContainer.addClassName("card-container");
        return cardsContainer;
    }
    private VerticalLayout buttonsAction(){
        VerticalLayout cardButton = new VerticalLayout();
        H3 title = new H3("Actions");

        Button btnCreate = new Button("Crear complement de cotizacio");
        Button btnApply = new Button("Solicitar full cotizacio anticipo");

        btnCreate.addClassName("btn-primary");
        btnApply.addClassName("btn-primary");

        cardButton.add(title,btnCreate,btnApply);
        cardButton.addClassName("card");
        return cardButton;
    }
    private Component showContributionReports(){
        H3 title = new H3("Dades de les declaracions");
        VerticalLayout contributionReports = new VerticalLayout(title,filterHeader(), new ContributionReportsGrid());
        contributionReports.addClassName("card");
        contributionReports.getStyle().setGap("0");
        return contributionReports;
    }
    private Component filterHeader(){
        Span periodLabel = new Span("Periode de referencia");
        periodLabel.addClassName("period-filter__label");

        MonthYearFilter filter= new MonthYearFilter();
        Div filterContent = new Div(periodLabel,filter);
        filterContent.addClassName("period-filter__content");

        Button filterSearchButton = new Button("Buscar Cotizacio",e -> {
            FilterRangeDTO dto = filter.getValue();
            System.out.println(dto.toString());
        });
        filterSearchButton.addClassNames("btn-primary","period-filter__search");

        Checkbox buttonToggleXML = new Checkbox("Mostrar accions XML");
        buttonToggleXML.addThemeName("toggle-button");
        Checkbox buttonTogglePending = new Checkbox("Mostrar nomes pendents");
        buttonTogglePending.addThemeName("toggle-button");

        Div filterTogglesActions = new Div(buttonToggleXML,buttonTogglePending);
        filterTogglesActions.addClassName("period-filter__toggles");

        Div header = new Div(filterContent,filterSearchButton, filterTogglesActions);
        header.addClassName("period-filter");
        return header;
    }
}
