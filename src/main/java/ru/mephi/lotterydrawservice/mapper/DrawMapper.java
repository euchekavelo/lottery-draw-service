package ru.mephi.lotterydrawservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.mephi.lotterydrawservice.dto.DrawDto;
import ru.mephi.lotterydrawservice.dto.request.DrawCreateRequestDto;
import ru.mephi.lotterydrawservice.dto.response.DrawCreateResponseDto;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DrawMapper {

    @Mapping(
            target = "status",
            source = "drawStatus"
    )
    Draw toDraw(DrawCreateRequestDto createRequestDto, DrawStatus drawStatus);

    DrawCreateResponseDto toDrawCreateResponseDto(Draw draw);

    DrawDto toDrawDto(Draw draw);
}
