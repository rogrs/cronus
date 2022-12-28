package br.com.rogrs.cronus.mapper;

import br.com.rogrs.cronus.dto.FilmDTO;
import br.com.rogrs.cronus.entity.Film;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmMapper {

   // FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

//    @Mapping(source = "id", target = "id")
    Film dtoToModel(FilmDTO filmDTO);

    @InheritInverseConfiguration
    FilmDTO modelToDto(Film film);

}
