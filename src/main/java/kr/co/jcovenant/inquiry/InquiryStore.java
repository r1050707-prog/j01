package kr.co.jcovenant.inquiry;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

@Component
public class InquiryStore {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

    private final AtomicLong sequence = new AtomicLong(1);
    private final Deque<Inquiry> items = new ConcurrentLinkedDeque<>();

    public Inquiry save(InquiryRequest request) {
        String email = request.email() == null ? "" : request.email().trim();
        Inquiry inquiry = new Inquiry(
                sequence.getAndIncrement(),
                request.name().trim(),
                request.organization().trim(),
                request.phone().trim(),
                email,
                request.message().trim(),
                ZonedDateTime.now(SEOUL)
        );
        items.addFirst(inquiry);
        return inquiry;
    }

    public List<Inquiry> findAll() {
        return new ArrayList<>(items);
    }
}
