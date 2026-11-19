package kr.co.jcovenant.inquiry;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    private final InquiryStore store;

    public InquiryController(InquiryStore store) {
        this.store = store;
    }

    @GetMapping
    public List<InquiryResponse> list() {
        return store.findAll().stream().map(InquiryResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InquiryResponse create(@Valid @RequestBody InquiryRequest request) {
        return InquiryResponse.from(store.save(request));
    }
}
