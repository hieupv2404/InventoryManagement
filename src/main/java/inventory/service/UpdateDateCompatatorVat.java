package inventory.service;

import inventory.model.Vat;

import java.util.Comparator;

public class UpdateDateCompatatorVat implements Comparator<Vat> {

    @Override
    public int compare(Vat o1, Vat o2) {
        return o1.getUpdateDate().compareTo(o2.getUpdateDate());
    }
}
