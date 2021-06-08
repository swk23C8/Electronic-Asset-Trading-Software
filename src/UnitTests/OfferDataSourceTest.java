package UnitTests;

import Common.Offer;
import Server.OfferDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OfferDataSourceTest {
    private OfferDataSource source;
    private Offer offer;
    private Offer test;

    @BeforeEach
    void set() {
        source = new OfferDataSource();
        offer = new Offer(011, "sell", "abc", "test2", 1, 1 );
        test = new Offer(101, "sell", "abc", "test2", 1, 1 );
        source.addOffer(offer);
    }



    @AfterEach
    void delete(){
        source.deleteOffer(011);
    }

    @Test
    void addOffer() {
        assertEquals(offer.getId(), source.getOffer(011).getId());
    }

    @Test
    void addHistory() {
        assertEquals(offer.getId(), source.getOffer(011).getId());
    }

    @Test
    void editQty() {
        int quantity = offer.getQuantity();
        source.editQty(new Offer(011,"sell", "abc", "test2", 100, 1));
        assertEquals(100, source.getOffer(011).getQuantity());

    }

    @Test
    void offerSet() {
        List<Offer> set = source.offerSet();
        source.addOffer(test);
        assertNotEquals(set, source.offerSet());
        source.deleteOffer(101);
    }

    @Test
    void getOffer() {
        assertEquals(offer, source.getOffer(011));
    }

    @Test
    void getSize() {
        int size = source.getSize();
        source.addOffer(test);
        assertNotEquals(size, source.getSize());
        source.deleteOffer(101);
    }

    @Test
    void deleteOffer() {
        source.addOffer(test);
        assertTrue(source.deleteOffer(101));
    }
}