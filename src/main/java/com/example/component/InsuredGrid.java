package com.example.component;

import com.example.backend.dto.InsuredDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class InsuredGrid extends VerticalLayout {
    public InsuredGrid(){
        Grid<InsuredDTO> grid = new Grid<>(InsuredDTO.class, false);

        grid.addColumn(InsuredDTO::ordre).setHeader("Ordre");
        grid.addColumn(InsuredDTO::number).setHeader("Num. Assegurat");
        grid.addColumn(InsuredDTO::name).setHeader("Nom Assegurat");
        grid.addColumn(InsuredDTO::percentage).setHeader("% Cotizacio");
        grid.addColumn(InsuredDTO::totalCASS).setHeader("Total CASS");
        grid.addColumn(InsuredDTO::contributionCASS).setHeader("Cotizacio CASS");
        grid.addColumn(InsuredDTO::totalIRPF).setHeader("Total IRPF");
        grid.addColumn(InsuredDTO::irpfWithHolding).setHeader("Retencio IRPF");
        grid.addComponentColumn(insuredDTO ->{
            Button editBtn = new Button(VaadinIcon.PENCIL.create(), event ->{
                DialogInsured dialog = new DialogInsured(insuredDTO);
                dialog.open();
            });
            editBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
            return editBtn;
        }).setHeader("Accions");
        grid.setItems(getMockData());
        grid.getStyle().setMarginBottom("0");
        grid.setWidthFull();
        add(grid);
    }

    private List<InsuredDTO> getMockData() {
        return List.of(
                new InsuredDTO(1, "001", "Josep Garcia Garcia", 100, 1500.00, 750.00, 980.00, 245.00),
                new InsuredDTO(2, "002", "Maria López Martínez", 80, 1200.50, 600.25, 750.00, 187.50),
                new InsuredDTO(3, "003", "Joan Pérez Sánchez", 100, 2300.00, 1150.00, 1450.00, 362.50),
                new InsuredDTO(4, "004", "Anna Fernández Costa", 60, 900.75, 450.38, 580.00, 145.00),
                new InsuredDTO(5, "005", "Pere Martí Puig", 100, 3100.00, 1550.00, 1980.00, 495.00),
                new InsuredDTO(6, "006", "Núria Soler Vidal", 90, 1800.25, 900.13, 1150.00, 287.50)
        );
    }
}
