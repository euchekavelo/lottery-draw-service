package ru.mephi.lotterydrawservice.service;

import ru.mephi.lotterydrawservice.dto.request.InvoiceRequestDto;
import ru.mephi.lotterydrawservice.dto.response.InvoiceResponseDto;
import ru.mephi.lotterydrawservice.exception.InvoiceNotFoundException;
import ru.mephi.lotterydrawservice.model.Invoice;

public interface InvoiceService {

    InvoiceResponseDto register(InvoiceRequestDto invoiceRequestDto);

    void cancelByDraw(long drawId) throws Exception;

    Invoice findInvoiceById(long invoiceId) throws InvoiceNotFoundException;

    Boolean checkDrawExistsAndActive(long drawId);
}
