package invoice.service.controller;

import invoice.service.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/invoices")
public class InvoiceController {

    @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String createInvoice(@RequestBody Invoice newInvoice){
        Invoice invoice = new Invoice();
        invoice.setAmount(newInvoice.getAmount());
        invoice.setDate(newInvoice.getDate());
        invoice.setName(newInvoice.getName());
        invoice.setId(newInvoice.getId());
        invoice.setPrice(newInvoice.getPrice());
        String location = "invoices";

        try{
            if(new File(location).mkdirs()) {
                System.out.println("Directory created");
            }
            File invoiceFile = new File(location + "/" + invoice.getId() + ".txt");
            if (invoiceFile.createNewFile()){
                System.out.println("File created: " + invoiceFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try{
            FileWriter invoiceWriter = new FileWriter(location + "/" + invoice.getId() + ".txt");
            invoiceWriter.write("Date: " + invoice.getDate() + "\n");
            invoiceWriter.write("Price for " + invoice.getAmount() + " of " + invoice.getName() + ":\n");
            invoiceWriter.write(invoice.getPrice() + "\n");
            invoiceWriter.close();
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "Invoice_created";
    }
}
