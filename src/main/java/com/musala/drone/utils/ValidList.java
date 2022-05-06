package com.musala.drone.utils;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.*;

/**
 * This class extends the List interface and allows us to validate a list of objects with javax validators and throw necessary exceptions which are caught by the exception handler
 * @param <E>
 */
@Data
public class ValidList<E> implements List<E> {

    @Valid
    @Delegate
    private List<E> list;

    public ValidList() {
        this.list = new ArrayList<E>();
    }

    public ValidList(List<E> list) {
        this.list = list;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }


}
