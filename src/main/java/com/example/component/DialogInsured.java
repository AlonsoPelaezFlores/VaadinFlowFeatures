package com.example.component;

import com.example.backend.dto.ConceptDTO;
import com.example.backend.dto.InsuredDTO;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class DialogInsured extends Dialog {
    Grid<ConceptDTO> grid = new Grid<>(ConceptDTO.class,false);
    public DialogInsured(InsuredDTO insuredDTO){
        conceptGrid();
        setHeaderTitle("Editar Debit");
        H3 titleDialog = new H3("Conceptes");
        add(titleDialog,grid,conceptForm());
        Button btnClose = new Button("Cancel·lar", e -> close());
        getFooter().add(btnClose);
    }

    private Component conceptForm() {
        H3 titleForm = new H3("Afegir");
        titleForm.getStyle().setMarginBottom("0");
        Select<String> conceptList = new Select<>("Concepte");
        String[] concepts= {"SBA","ARV","RES","CPP","PRE","CEC","CEI"};
        conceptList.setItems(concepts);
        conceptList.setPlaceholder("Escull un concepte");
        TextField amountField = new TextField("Import");
        TextField hoursField = new TextField("Hores");
        TextField daysField = new TextField("Dies");
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
           Button btnDelete = new Button(VaadinIcon.TRASH.create(),e-> confirmAction(conceptDTO));

           btnEdit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
           btnDelete.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

           return new HorizontalLayout(btnEdit,btnDelete);
        }).setHeader("Accions");
        grid.setAllRowsVisible(true);
        grid.getStyle().setMarginBottom("4rem");
        grid.setItems(getMockData());
    }

    private void confirmAction(ConceptDTO dto) {
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
