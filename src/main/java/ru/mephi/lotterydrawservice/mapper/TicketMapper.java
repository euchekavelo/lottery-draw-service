package ru.mephi.lotterydrawservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.mephi.lotterydrawservice.dto.response.TicketResponseDto;
import ru.mephi.lotterydrawservice.model.Ticket;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "draw.id", target = "drawId")
    TicketResponseDto ticketToTicketResponseDto(Ticket ticket);

    List<TicketResponseDto> ticketsToTicketResponseDtoList(List<Ticket> tickets);
}
