package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		List<Sale> sale = new ArrayList<>();
		Map<String, Double> map = new HashMap<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] fields = line.split(",");

				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				sale.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}

			System.out.println("\nTotal de vendas por vendedor: ");

			for (Sale s : sale) {
				map.put(s.getSeller(), 0.00);
			}

			for (String key : map.keySet()) {
				Double total = sale.stream().filter(x -> x.getSeller().equals(key)).map(x -> x.getTotal()).reduce(0.00,
						(x, y) -> x + y);
				map.put(key, total);
				System.out.println(key + " - R$ " + String.format("%.2f", total));
			}

		} catch (Exception e) {
			System.out.println("Erro " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();
	}

}