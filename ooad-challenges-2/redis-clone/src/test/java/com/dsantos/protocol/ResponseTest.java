package com.dsantos.protocol;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class ResponseTest {
    @Test
    void okResponse() {
        assertEquals("+OK\r\n", Response.ok().toString());
    }
    @Test
    void errorResponse() {
        assertEquals("-ERR something went wrong\r\n", Response.error("something went wrong").toString());
    }
    @Test
    void nilResponse() {
        assertEquals("$-1\r\n", Response.nil().toString());
    }
    @Test
    void bulkResponse() {
        assertEquals("$5\r\nhello\r\n", Response.bulk("hello").toString());
    }
    @Test
    void integerResponse() {
        assertEquals(":42\r\n", Response.integer(42).toString());
    }
    @Test
    void arrayResponse() {
        String result = Response.array(List.of("name", "age")).toString();
        assertEquals("*2\r\n$4\r\nname\r\n$3\r\nage\r\n", result);
    }
    @Test
    void emptyArrayResponse() {
        assertEquals("*0\r\n", Response.array(List.of()).toString());
    }
}
