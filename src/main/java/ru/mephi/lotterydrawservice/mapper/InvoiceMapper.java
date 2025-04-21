package ru.mephi.lotterydrawservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.mephi.lotterydrawservice.dto.response.InvoiceResponseDto;
import ru.mephi.lotterydrawservice.model.Invoice;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceMapper {

    InvoiceResponseDto invoiceToInvoiceResponseDto(Invoice invoice);
}
