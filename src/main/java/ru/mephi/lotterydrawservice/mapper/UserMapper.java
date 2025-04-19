package ru.mephi.lotterydrawservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mephi.lotterydrawservice.dto.request.UserRequestDto;
import ru.mephi.lotterydrawservice.dto.response.UserResponseDto;
import ru.mephi.lotterydrawservice.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract User userRequestDtoToUser(UserRequestDto userRequestDto);

    public abstract UserResponseDto userToUserResponseDto(User user);

    @Named("encodePassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
