package com.dsantos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Main {
    static void main() {
        var arena = new Venue("City Arena", Map.of(
                Zone.VIP, 20, Zone.PREMIUM, 50, Zone.REGULAR, 100, Zone.ECONOMY, 80
        ));

        var rockConcert = new Show("Rock Concert", arena, LocalDateTime.of(2025, 3, 15, 20, 0));
        var jazzNight = new Show("Jazz Night", arena, LocalDateTime.of(2025, 3, 20, 19, 30));

        var system = new TicketSystem();
        system.addShow(rockConcert);
        system.addShow(jazzNight);

        system.sellTicket(rockConcert, Zone.VIP, 1);
        system.sellTicket(rockConcert, Zone.VIP, 5);
        system.sellTickets(rockConcert, Zone.REGULAR, 5);
        system.sellTickets(jazzNight, Zone.PREMIUM, 3);

        system.sellTicket(rockConcert, Zone.VIP, 1);

        System.out.println("\n=== Summary ===");
        int totalTickets = system.getShows().stream()
                .mapToInt(system::getTotalTicketsSold)
                .sum();

        var totalRevenue = system.getShows().stream()
                .map(system::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total tickets sold: " + totalTickets);
        System.out.printf("Total revenue: $%.0f%n", totalRevenue);

        System.out.println("\nPer show:");
        for (var show : system.getShows()) {
            System.out.printf("  %s: %d tickets, $%.0f%n",
                    show.getName(), system.getTotalTicketsSold(show), system.getTotalRevenue(show));
        }
    }
}
