package com.dsantos;

import java.nio.file.Paths;
import java.util.UUID;

public class Main {
    static void main() {
        CryptoService crypto = new CryptoService();
        FileRepository repo = new FileRepository();
        LocalFileShareService svc = new LocalFileShareService(Paths.get("storage"), crypto, repo);

        UUID id = svc.save("greeting.txt", "Hello World".getBytes());
        System.out.println("Saved id: " + id);

        svc.listFiles().forEach(System.out::println);

        byte[] restored = svc.restore(id);
        System.out.println("Restored content: " + new String(restored));

        boolean deleted = svc.delete(id);
        System.out.println("Deleted: " + deleted);
    }
}
