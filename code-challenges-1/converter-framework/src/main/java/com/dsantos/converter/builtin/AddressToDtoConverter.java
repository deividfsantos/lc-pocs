package com.dsantos.converter.builtin;
import com.dsantos.converter.Converter;
import com.dsantos.model.Address;
import com.dsantos.model.AddressDto;
public class AddressToDtoConverter implements Converter<Address, AddressDto> {
    @Override
    public AddressDto convert(Address source) {
        if (source == null) {
            throw new IllegalArgumentException("Source address must not be null");
        }
        return new AddressDto(
            source.getStreet(),
            source.getCity(),
            source.getState(),
            source.getZipCode()
        );
    }
}
