package ru.mephi.lotterydrawservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.mephi.lotterydrawservice.dto.request.PaymentRequestDto;
import ru.mephi.lotterydrawservice.dto.response.PaymentResponseDto;
import ru.mephi.lotterydrawservice.exception.InvalidPaymentDataException;
import ru.mephi.lotterydrawservice.exception.InvoiceAlreadyProcessedException;
import ru.mephi.lotterydrawservice.mapper.PaymentMapper;
import ru.mephi.lotterydrawservice.model.Invoice;
import ru.mephi.lotterydrawservice.model.Payment;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.model.enums.InvoiceStatus;
import ru.mephi.lotterydrawservice.model.enums.PaymentStatus;
import ru.mephi.lotterydrawservice.repository.PaymentRepository;
import ru.mephi.lotterydrawservice.security.AuthUser;
import ru.mephi.lotterydrawservice.service.InvoiceService;
import ru.mephi.lotterydrawservice.service.PaymentService;
import ru.mephi.lotterydrawservice.service.TicketService;

import java.security.SecureRandom;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final InvoiceService invoiceService;
    private final TicketService ticketService;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper,
                              InvoiceService invoiceService, TicketService ticketService) {

        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.invoiceService = invoiceService;
        this.ticketService = ticketService;
    }

    @Override
    public PaymentResponseDto pay(PaymentRequestDto paymentRequestDto) {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = authUser.getUser();

        Invoice invoice = invoiceService.findInvoiceById(paymentRequestDto.getInvoiceId());
        if (invoice.getStatus() != InvoiceStatus.PENDING) {
            throw new InvoiceAlreadyProcessedException("Invoice is not in a valid state for payment processing.");
        }

        if (!isCardDataValid(paymentRequestDto.getCardNumber(), paymentRequestDto.getCvc())) {
            throw new InvalidPaymentDataException("Invalid card data provided.");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(paymentRequestDto.getAmount());

        if (isPaymentSuccess()) {
            payment.setStatus(PaymentStatus.SUCCESS);
            try {
                ticketService.createTicket(user, invoice.getTicketData());
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentMapper.paymentToPaymentResponseDto(paymentRepository.save(payment));
    }

    private boolean isCardDataValid(String cardNumber, String cvc) {
        if (cardNumber == null || cvc == null) {
            return false;
        }

        if (!cardNumber.matches("\\d{12,19}")) {
            return false;
        }

        return cvc.equals("123");
    }

    private boolean isPaymentSuccess() {
        return new SecureRandom().nextInt(10) < 8;
    }
}
