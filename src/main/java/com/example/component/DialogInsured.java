package com.example.component;

import com.example.backend.dto.ConceptContribution;
import com.example.backend.dto.AddConceptForm;
import com.example.backend.dto.ConceptDTO;
import com.example.backend.dto.InsuredDTO;
import com.example.backend.validator.DaysValidator;
import com.example.backend.validator.HoursValidator;
import com.example.backend.validator.ImportValidator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class DialogInsured extends Dialog {
    Grid<ConceptDTO> grid = new Grid<>(ConceptDTO.class,false);
    Binder<AddConceptForm> binderForm = new Binder<>(AddConceptForm.class);
    private final Select<ConceptContribution> conceptList = new Select<>("Concepte");
    private final TextField amountField = new TextField("Import");
    private final IntegerField hoursField = new IntegerField("Hores");
    private final IntegerField daysField = new IntegerField("Dies");
    public DialogInsured(InsuredDTO insuredDTO){
        setUp();
        conceptGrid();
        setHeaderTitle("Editar Debit");
        H3 titleDialog = new H3("Conceptes");
        add(titleDialog,grid,conceptForm());
        Button btnClose = new Button("Cancel·lar", e -> close());
        getFooter().add(btnClose);
    }
    private void setUp(){
        configureBinder();
        configureSelectBinder();
    }
    private void configureBinder(){

        binderForm
                .forField(conceptList)
                .asRequired()
                .bind(AddConceptForm::getConcept,AddConceptForm::setConcept);
        binderForm
                .forField(amountField)
                .asRequired()
                .withValidator(new ImportValidator(null))
                .bind(AddConceptForm::getAmount,AddConceptForm::setAmount);

        binderForm
                .forField(hoursField)
                .withValidator(new HoursValidator())
                .bind(AddConceptForm::getHours,AddConceptForm::setHours);

        binderForm.forField(daysField)
                .withValidator(new DaysValidator())
                .bind(AddConceptForm::getDays,AddConceptForm::setDays);

    }

    private void configureSelectBinder() {
        conceptList.addValueChangeListener( e -> {
            ConceptContribution value = e.getValue();
            binderForm
                    .forField(amountField)
                    .asRequired()
                    .withValidator(new ImportValidator(value.name()))
                    .bind(AddConceptForm::getAmount,AddConceptForm::setAmount);
            applyChangeUIByConcept(value);
        });

    }

    private void applyChangeUIByConcept(ConceptContribution value) {
        cleanFields();
        switch (value){
            case SBA:
                activeField(hoursField,false);
                activeField(daysField,false);
                break;
            case SBD:
                activeField(hoursField,true);
                activeField(daysField,true);
                break;
            case PRE, CEC:
                activeField(hoursField,false);
                activeField(daysField,true);
                break;
            default:
                Notification.show("Error! Concepte no valid");
                break;
        }
    }
    private void cleanFields() {
        amountField.clear();
        amountField.setInvalid(false);
        amountField.setErrorMessage(null);

        hoursField.clear();
        hoursField.setErrorMessage(null);
        hoursField.setInvalid(false);

        daysField.clear();
        daysField.setErrorMessage(null);
        daysField.setInvalid(false);
    }
    private void activeField(IntegerField field, boolean status){
        if (status){
            field.setEnabled(true);
            field.setRequired(true);
        }else {
            field.setRequired(false);
            field.setEnabled(false);
        }
    }


    private Component conceptForm() {

        H3 titleForm = new H3("Afegir");
        titleForm.getStyle().setMarginBottom("0");

        conceptList.setNoVerticalOverlap(true);
        conceptList.setItems(ConceptContribution.SBA,ConceptContribution.SBD,ConceptContribution.CEC,ConceptContribution.PRE);
        conceptList.setPlaceholder("Escull un concepte");

        amountField.setSuffixComponent(new Span("€"));

        Button btnAdd = new Button("Afegir");

        btnAdd.addClassName("btn-primary");

        HorizontalLayout form = new HorizontalLayout(conceptList,amountField, hoursField, daysField,btnAdd);
        form.setAlignItems(FlexComponent.Alignment.BASELINE);
        VerticalLayout layout = new VerticalLayout(titleForm,form);
        layout.getStyle().setGap("0");
        return layout;
    }

    private void conceptGrid(){
        grid.addColumn(ConceptDTO::concept).setHeader("Concepte").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::amount).setHeader("Import").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::hours).setHeader("Hores").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::days).setHeader("Dies").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::percentageCASS).setHeader("% CASS").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::contribution).setHeader("Cotizació").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::percentageIRPF).setHeader("% IRPF").setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn(ConceptDTO::withHolding).setHeader("Retenció").setTextAlign(ColumnTextAlign.CENTER);
        grid.addComponentColumn(conceptDTO -> {
           Button btnEdit = new Button(VaadinIcon.PENCIL.create());
           Button btnDelete = new Button(VaadinIcon.TRASH.create(),e-> deleteConceptAction(conceptDTO));

           btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
           btnDelete.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

           return new HorizontalLayout(btnEdit,btnDelete);
        }).setHeader("Accions");
        grid.setAllRowsVisible(true);
        grid.getStyle().setMarginBottom("4rem");
        grid.setItems(getMockData());
    }

    private void deleteConceptAction(ConceptDTO dto) {
        ConfirmDialog confirm= new ConfirmDialog();
        confirm.setHeader("Eliminar Concepte");
        confirm.setText("Vols eliminar el concepte " + dto.concept() + " ?");
        Button btnConfirm = new Button("Confirmar", e -> confirm.close());
        btnConfirm.addClassName("btn-primary");
        Button btnCancel = new Button("Cancel·lar",e -> confirm.close());
        confirm.setCancelable(true);
        confirm.setConfirmButton(btnConfirm);
        confirm.setCancelButton(btnCancel);
        confirm.open();
    }

    private List<ConceptDTO> getMockData() {
        return List.of(
                new ConceptDTO("SBA", 1500.00, 160, 20, 28, 420, 15, 225),
                new ConceptDTO("ARV", 250.00, 20, 0, 28, 70, 15, 38)
                );
    }
}
