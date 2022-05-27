
package ru.prmu.constructor.mapper;

public interface AMapper<D , E> {

    D toDTO(E e);

    E fromDTO(D dto);
}
