package cca.transfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class TransferController implements Initializable {
    
    @FXML private TableView<Resident> residentTable;
    @FXML private TableColumn<Resident, Integer> 
        refNo, unitType, sex1, sex2, decline, fso, contract;
    @FXML private TableColumn<Resident, String> 
        last, first, unitNo;
    @FXML private TableColumn<Resident, LocalDate> 
        birthDate1, birthDate2, entryDate1, entryDate2, 
        deathDate1, deathDate2,  termDate1, termDate2;
    @FXML private TableColumn<Resident, Double> 
        entryFee1, entryFee2, nonrefFee1, nonrefFee2, 
        refundFee1, refundFee2, comFee1, comFee2;

    @FXML private TableView<TransferResident> transferTable;
    @FXML private TableColumn<TransferResident, Integer> 
        transferRefNo, res, into;
    @FXML private TableColumn<TransferResident, String> 
        transferLast, transferFirst;
    @FXML private TableColumn<TransferResident, LocalDate> date;

    @FXML private Label status;
    @FXML private Button 
        copyTransfersButton, copyResidentsButton, exportButton;

    ObservableList<Resident> residentList = FXCollections.observableArrayList();
    ObservableList<TransferResident> transferList = FXCollections.observableArrayList();
    /*
     * TO DO
     * 4 and 5 Level radio buttons
     * Decline steps in the info tab
     * Graceful cancel
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set columns for resident table
        refNo.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("refNo"));
        last.setCellValueFactory(new PropertyValueFactory<Resident, String>("last"));
        first.setCellValueFactory(new PropertyValueFactory<Resident, String>("first"));
        unitNo.setCellValueFactory(new PropertyValueFactory<Resident, String>("unitNo"));
        unitType.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("unitType"));
        sex1.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("sex1"));
        sex2.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("sex2"));
        birthDate1.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("birthDate1"));
        birthDate2.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("birthDate2"));
        entryDate1.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("entryDate1"));
        entryDate2.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("entryDate2"));
        entryFee1.setCellValueFactory(new PropertyValueFactory<Resident, Double>("entryFee1"));
        entryFee2.setCellValueFactory(new PropertyValueFactory<Resident, Double>("entryFee2"));
        deathDate1.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("deathDate1"));
        deathDate2.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("deathDate2"));
        termDate1.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("termDate1"));
        termDate2.setCellValueFactory(new PropertyValueFactory<Resident, LocalDate>("termDate2"));
        nonrefFee1.setCellValueFactory(new PropertyValueFactory<Resident, Double>("nonrefFee1"));
        nonrefFee2.setCellValueFactory(new PropertyValueFactory<Resident, Double>("nonrefFee2"));
        refundFee1.setCellValueFactory(new PropertyValueFactory<Resident, Double>("refundFee1"));
        refundFee2.setCellValueFactory(new PropertyValueFactory<Resident, Double>("refundFee2"));
        comFee1.setCellValueFactory(new PropertyValueFactory<Resident, Double>("comFee1"));
        comFee2.setCellValueFactory(new PropertyValueFactory<Resident, Double>("comFee2"));
        decline.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("decline"));
        fso.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("fso"));
        contract.setCellValueFactory(new PropertyValueFactory<Resident, Integer>("contract"));

        // Set columns for transfer table
        transferRefNo.setCellValueFactory(new PropertyValueFactory<TransferResident, Integer>("refNo"));
        transferLast.setCellValueFactory(new PropertyValueFactory<TransferResident, String>("last"));
        transferFirst.setCellValueFactory(new PropertyValueFactory<TransferResident, String>("first"));
        res.setCellValueFactory(new PropertyValueFactory<TransferResident, Integer>("res"));
        into.setCellValueFactory(new PropertyValueFactory<TransferResident, Integer>("into"));
        date.setCellValueFactory(new PropertyValueFactory<TransferResident, LocalDate>("date"));

        // Attach the lists to their respective table
        residentTable.setItems(residentList);
        transferTable.setItems(transferList);

    }
    
    // importSpreadsheet(): open the spreadsheet containing the resident data
    //                      and convert it into the table format
    @FXML
    private void importSpreadsheet() {

        // Set the FileChooser to get the requested .xlsx file
        status.setText("Importing...");
        FileChooser fc = new FileChooser();
        fc.setTitle("Import Database");
        fc.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
        File home = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(home);
        
        try {
            // Get the spreadsheet from the user and open the INPUT sheet
            FileInputStream excel = new FileInputStream(fc.showOpenDialog(transferTable.getScene().getWindow()));
            XSSFWorkbook workbook = new XSSFWorkbook(excel);
            XSSFSheet sheet = workbook.getSheet("INPUT");
            
            // Call the function to import the Residents and transfer tables
            importResidents(sheet);

            // Close the stream to the spreadsheet
            workbook.close();
            excel.close();

            // Write success message and give user access to the function buttons.
            status.setText("Database imported successfully");
            copyTransfersButton.setDisable(false);
            copyResidentsButton.setDisable(false);
            exportButton.setDisable(false);

        } catch (IOException|IllegalStateException e) {
            // If exception, print message to status
            status.setText("" + e);
        } catch (NullPointerException e) {
            // If canceled, don't display error message
            status.setText("");
        }
    }

    // importResidents(): access the resident data and add it to a Resident
    //                    data entry to display in the Resident table
    private void importResidents(XSSFSheet sheet) {

        residentList.clear();
        Row row;
        String unitNo;
        int sex1, sex2, decline, contract;
        LocalDate birthDate1, birthDate2, entryDate1, entryDate2, 
            deathDate1, deathDate2, termDate1, termDate2, 
            transfer1to2, transfer1to3, transfer2to2, transfer2to3;
        double entryFee1, nonrefFee1, refundFee1, comFee1;
        Date bd1, bd2, ed1, ed2, dd1, dd2, td1, td2, t12, t13, t22, t23;

        List<Integer> declineList = getDecline(sheet.getWorkbook());

        for (int i = 9; i <= sheet.getLastRowNum(); i++) {
            // Get the current row: if Last Name is null, end the loop
            row = sheet.getRow(i);

            // If person 1's last name is null, end the loop 
            // (the end of the list has been reached even if the sheet hasn't)
            if (row.getCell(1).getStringCellValue().equals("")) {
                break;
            }

            // Format empty cells to avoid errors
            Cell cell;
            for (int j = 0; j < row.getLastCellNum(); j++) {

                // Get the current cell
                cell = row.getCell(j);

                // If the cell doesn't exist, create a blank cell
                if (cell == null) {
                    row.createCell(j);

                // If the cell is an empty string, make it blank instead
                } else if (cell.getCellType().equals(CellType.STRING) && cell.getStringCellValue().equals("")) {
                    cell.setBlank();
                }
            }

            // If the unit number is just an integer, convert to a string
            if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                unitNo = "" + (int) row.getCell(3).getNumericCellValue();
            } else {
                unitNo = row.getCell(3).getStringCellValue();
            }

            // Check if the sex of 1st and 2nd is male, female, or null
            sex1 = gender(row.getCell(6).getStringCellValue());
            sex2 = gender(row.getCell(13).getStringCellValue());

            // Get date values from the Birth, Entry, Death, and Termin cells
            bd1 = row.getCell(7).getDateCellValue();
            bd2 = row.getCell(14).getDateCellValue();
            ed1 = row.getCell(8).getDateCellValue();
            ed2 = row.getCell(15).getDateCellValue();
            dd1 = row.getCell(11).getDateCellValue();
            dd2 = row.getCell(18).getDateCellValue();
            td1 = row.getCell(12).getDateCellValue();
            td2 = row.getCell(19).getDateCellValue();
            t12 = row.getCell(9).getDateCellValue();
            t13 = row.getCell(10).getDateCellValue();
            t22 = row.getCell(16).getDateCellValue();
            t23 = row.getCell(17).getDateCellValue();
            
            // If the date value is not null, convert to LocalDate
            // otherwise leave null
            birthDate1 = ((bd1 == null) ? null : bd1.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            birthDate2 = ((bd2 == null) ? null : bd2.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            entryDate1 = ((ed1 == null) ? null : ed1.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            entryDate2 = ((ed2 == null) ? null : ed2.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            deathDate1 = ((dd1 == null) ? null : dd1.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            deathDate2 = ((dd2 == null) ? null : dd2.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            termDate1  = ((td1 == null) ? null : td1.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            termDate2  = ((td2 == null) ? null : td2.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            transfer1to2  = ((t12 == null) ? null : t12.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            transfer1to3  = ((t13 == null) ? null : t13.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            transfer2to2  = ((t22 == null) ? null : t22.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
            transfer2to3  = ((t23 == null) ? null : t23.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());

            // Get fees for person 1 (person 2 always set to 0.0)
            entryFee1 = row.getCell(20).getNumericCellValue();
            nonrefFee1 = row.getCell(21).getNumericCellValue();
            refundFee1 = row.getCell(22).getNumericCellValue();
            comFee1 = row.getCell(23).getNumericCellValue();

            // Get the unit type, and use the index to find the decline result
            contract = (int) row.getCell(5).getNumericCellValue();
            decline = declineList.get(contract);
            
            // Create a new Resident entry and add it to the ResidentList
            residentList.add(new Resident(
                /* refNo */ i-9,
                /* last */ row.getCell(1).getStringCellValue(),
                /* first */ row.getCell(2).getStringCellValue(),
                unitNo, 
                /*unitType*/ (int) row.getCell(4).getNumericCellValue(), 
                sex1, sex2, birthDate1, birthDate2, entryDate1, entryDate2,
                entryFee1, /*entryFee2*/ 0.0, 
                deathDate1, deathDate2, termDate1, termDate2, 
                nonrefFee1, /* nonrefFee2 */ 0.0, 
                refundFee1, /*refundFee2*/ 0.0, 
                comFee1, /* comFee2 */ 0.0, 
                decline, /* fso */ 0, contract,
                transfer1to2, transfer1to3, transfer2to2, transfer2to3));

        }

        // Import the transfers using the newly created residentList
        importTransfers();
    }

    
    // importTransfers(): access the residentList and add any transfers
    //                    found into the transferList as a new entry
    private void importTransfers() {
        
        // Clear the current transfer list
        transferList.clear();
        Resident resident;

        // Go through the entire residentList
        for (int i = 0; i < residentList.size(); i++) {
            // Get the resident at the current index
            resident = residentList.get(i);

            // Call addTransfer for each of the potential transfers
            addTransfer(resident, 1, 2, resident.transfer1to2);
            addTransfer(resident, 1, 3, resident.transfer1to3);
            addTransfer(resident, 2, 2, resident.transfer2to2);
            addTransfer(resident, 2, 3, resident.transfer2to3);

        }
        
    }

    // addTransfer(): add an entry to transferList if the 
    //                given transfer field exists
    private void addTransfer(Resident resident, int res, int into, LocalDate transfer) {
        
        // if the transfer date exists, add an entry to transferList
        // with the information for the transferred resident
        if (transfer != null) {
            transferList.add(new TransferResident(resident.refNo, resident.last, resident.first, res, into, transfer));
        }
        
    }

    // copyResidents(): copy the residentList to the clipboard
    @FXML
    private void copyResidents() throws IOException {        
        
        // Set the status message and get the clipboard
        status.setText("Copying to clipboard...");
        String db = "";
        Clipboard clip = Clipboard.getSystemClipboard();
        ClipboardContent x = new ClipboardContent();
        
        // Add each of the RLA values to a string with linebreaks
        for (int i = 0; i < residentList.size(); i++) {
            Resident resident = residentList.get(i);
            db +=
                resident.getRefNo() + "\t" +
                resident.getLast() + "\t" +
                resident.getFirst() + "\t" +
                resident.getUnitNo() + "\t" +
                resident.getUnitType() + "\t" +
                resident.getSex1() + "\t" +
                resident.getSex2() + "\t" +
                resident.getBirthDate1() + "\t" +
                resident.getBirthDate2() + "\t" +
                resident.getEntryDate1() + "\t" +
                resident.getEntryDate2() + "\t" +
                resident.getEntryFee1() + "\t" +
                resident.getEntryFee2() + "\t" +
                resident.getDeathDate1() + "\t" +
                resident.getDeathDate2() + "\t" +
                resident.getTermDate1() + "\t" +
                resident.getTermDate2() + "\t" +
                resident.getNonrefFee1() + "\t" +
                resident.getNonrefFee2() + "\t" +
                resident.getRefundFee1() + "\t" +
                resident.getRefundFee2() + "\t" +
                resident.getComFee1() + "\t" +
                resident.getComFee2() + "\t" +
                resident.getDecline() + "\t" +
                resident.getFso() + "\t" +
                resident.getContract() + "\n";
        }

        // Replace null cells with empty cells
        db = db.replace("null\t", "\t");
        
        // Add the RLA value string to the clipboard
        x.putString(db);
        clip.setContent(x);
        status.setText("Copied to clipboard");
    }

    // copyTransfers(): Copy the transferList to the clipboard
    @FXML
    private void copyTransfers() throws IOException {        
        
        // Set the status message and get the clipboard
        status.setText("Copying to clipboard...");
        String db = "";
        Clipboard clip = Clipboard.getSystemClipboard();
        ClipboardContent x = new ClipboardContent();
        
        // Add each of the RLA values to a string with linebreaks
        for (int i = 0; i < transferList.size(); i++) {
            TransferResident resident = transferList.get(i);
            db +=
                resident.getRefNo() + "\t" +
                resident.getLast() + "\t" +
                resident.getFirst() + "\t" +
                resident.getRes() + "\t" +
                resident.getInto() + "\t" + 
                resident.getDate() + "\n";
        }
        
        // Add the RLA value string to the clipboard
        x.putString(db);
        clip.setContent(x);
        status.setText("Copied to clipboard");
    }

    // export(): export the resident and transfer tables to a new Excel sheet
    @FXML
    private void export() {
        
        status.setText("Exporting...");
        FileChooser fc = new FileChooser();
        fc.setTitle("Export Transfer Table");
        fc.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
        File home = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(home);
        File excel = fc.showSaveDialog(transferTable.getScene().getWindow());

        try {
            
            XSSFWorkbook workbook;

            // Create the file if it does not currently exist
            if (excel.createNewFile()) {
                workbook = new XSSFWorkbook();
            } else {
            // Else access and create a local copy of the spreadsheet
                FileInputStream excelIn = new FileInputStream(excel);
                workbook = new XSSFWorkbook(excelIn);
                excelIn.close();
            }
            
            // Copy the values into the workbook
            exportResidents(workbook);
            exportTransfers(workbook);
            
            // Output the values to the Excel spreadsheet
            FileOutputStream excelOut = new FileOutputStream(excel);
            workbook.write(excelOut);

            // Close the streams and set success message
            workbook.close();
            excelOut.close();
            status.setText("Table exported successfully");

        } catch (IOException e) {
            // If exception, print message to status
            status.setText("" + e);
        } catch (NullPointerException e) {
            // If canceled, don't display error message
            status.setText("");
        }
    }

    // exportResidents(): create a new Excel sheet and copy 
    //                    the ResidentList onto it
    private void exportResidents(XSSFWorkbook workbook) {
        
        XSSFSheet sheet;
        Row row;
        Resident resident;
        
        // Create the Residents sheet if it does not exist, otherwise reformat it
        if (workbook.getSheet("Residents") == null) {
            sheet = workbook.createSheet("Residents");
        } else {
            sheet = workbook.getSheet("Residents");
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i));
            }
        }
        
        // Move Residents to the first position and create the header row
        workbook.setSheetOrder("Residents", 0);
        row = sheet.createRow(0);

        // Set headers for the row
        row.createCell(0).setCellValue("Ref #");
        row.createCell(1).setCellValue("Last");
        row.createCell(2).setCellValue("First");
        row.createCell(3).setCellValue("Unit #");
        row.createCell(4).setCellValue("Unit Type");
        row.createCell(5).setCellValue("Sex of 1st");
        row.createCell(6).setCellValue("Sex of 2nd");
        row.createCell(7).setCellValue("Birth date of 1st");
        row.createCell(8).setCellValue("Birth date of 2nd");
        row.createCell(9).setCellValue("Entry date of 1st");
        row.createCell(10).setCellValue("Entry date of 2nd");
        row.createCell(11).setCellValue("Entry fee of 1st");
        row.createCell(12).setCellValue("Entry fee of 2nd");
        row.createCell(13).setCellValue("Death date of 1st");
        row.createCell(14).setCellValue("Death date of 2nd");
        row.createCell(15).setCellValue("Termin date of 1st");
        row.createCell(16).setCellValue("Termin date of 2nd");
        row.createCell(17).setCellValue("Nonref entry of 1st");
        row.createCell(18).setCellValue("Nonref entry of 2nd");
        row.createCell(19).setCellValue("Refund entry fee of 1st");
        row.createCell(20).setCellValue("Refund entry fee of 2nd");
        row.createCell(21).setCellValue("Commission on fee for 1st insured");
        row.createCell(22).setCellValue("Commission on fee for 2nd insured");
        row.createCell(23).setCellValue("Declining refund?");
        row.createCell(24).setCellValue("Special FSO Indicator");
        row.createCell(25).setCellValue("Contract type");
        row.createCell(26).setCellValue("Misc. notes");

        // Set styling for the row
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i <= 26; i++) {
            row.getCell(i).setCellStyle(headerStyle);
        }
        
        // Create format styles
        CellStyle dateStyle = workbook.createCellStyle();
        CellStyle moneyStyle = workbook.createCellStyle();
        CreationHelper ch = workbook.getCreationHelper();
        dateStyle.setDataFormat(ch.createDataFormat().getFormat("mm/dd/yyyy"));
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        moneyStyle.setDataFormat(ch.createDataFormat().getFormat("#,##0.00"));

        // Set date formatting
        sheet.setDefaultColumnStyle(7, dateStyle);
        sheet.setDefaultColumnStyle(8, dateStyle);
        sheet.setDefaultColumnStyle(9, dateStyle);
        sheet.setDefaultColumnStyle(10, dateStyle);
        sheet.setDefaultColumnStyle(13, dateStyle);
        sheet.setDefaultColumnStyle(14, dateStyle);
        sheet.setDefaultColumnStyle(15, dateStyle);
        sheet.setDefaultColumnStyle(16, dateStyle);

        // Set money formatting
        sheet.setDefaultColumnStyle(11, moneyStyle);
        sheet.setDefaultColumnStyle(12, moneyStyle);
        sheet.setDefaultColumnStyle(17, moneyStyle);
        sheet.setDefaultColumnStyle(18, moneyStyle);
        sheet.setDefaultColumnStyle(19, moneyStyle);
        sheet.setDefaultColumnStyle(20, moneyStyle);
        sheet.setDefaultColumnStyle(21, moneyStyle);
        sheet.setDefaultColumnStyle(22, moneyStyle);

        // Iterate through the residentList and add each entry as an Excel row
        for (int i = 1; i < residentList.size(); i++) {
            row = sheet.createRow(i);
            resident = residentList.get(i-1);
            row.createCell(0).setCellValue(resident.getRefNo());
            row.createCell(1).setCellValue(resident.getLast());
            row.createCell(2).setCellValue(resident.getFirst());
            row.createCell(3).setCellValue(resident.getUnitNo());
            row.createCell(4).setCellValue(resident.getUnitType());
            row.createCell(5).setCellValue(resident.getSex1());
            row.createCell(6).setCellValue(resident.getSex2());
            row.createCell(7).setCellValue(resident.getBirthDate1());
            row.createCell(8).setCellValue(resident.getBirthDate2());
            row.createCell(9).setCellValue(resident.getEntryDate1());
            row.createCell(10).setCellValue(resident.getEntryDate2());
            row.createCell(11).setCellValue(resident.getEntryFee1());
            row.createCell(12).setCellValue(resident.getEntryFee2());
            row.createCell(13).setCellValue(resident.getDeathDate1());
            row.createCell(14).setCellValue(resident.getDeathDate2());
            row.createCell(15).setCellValue(resident.getTermDate1());
            row.createCell(16).setCellValue(resident.getTermDate2());
            row.createCell(17).setCellValue(resident.getNonrefFee1());
            row.createCell(18).setCellValue(resident.getNonrefFee2());
            row.createCell(19).setCellValue(resident.getRefundFee1());
            row.createCell(20).setCellValue(resident.getRefundFee2());
            row.createCell(21).setCellValue(resident.getComFee1());
            row.createCell(22).setCellValue(resident.getComFee2());
            row.createCell(23).setCellValue(resident.getDecline());
            row.createCell(24).setCellValue(resident.getFso());
            row.createCell(25).setCellValue(resident.getContract());
        }

        for (int i = 0; i <= 26; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    // exportTransfers(): create a new Excel sheet and copy 
    //                    the TransferList onto it
    private void exportTransfers(XSSFWorkbook workbook) {
        
        XSSFSheet sheet;
        Row row;
        TransferResident resident;
            
        // Create the Transfers sheet if it does not exist, otherwise reformat it
        if (workbook.getSheet("Transfers") == null) {
            sheet = workbook.createSheet("Transfers");
        } else {
            sheet = workbook.getSheet("Transfers");
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i));
            }
        }

        // Move Transfers to the second position and create the header row
        workbook.setSheetOrder("Transfers", 1);
        row = sheet.createRow(0);

        // Set headers for the row
        row.createCell(0).setCellValue("Ref #");
        row.createCell(1).setCellValue("Last");
        row.createCell(2).setCellValue("First");
        row.createCell(3).setCellValue("Res");
        row.createCell(4).setCellValue("Into");
        row.createCell(5).setCellValue("Date");
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        // Set styling for the row
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i <= 5; i++) {
            row.getCell(i).setCellStyle(headerStyle);
        }
        
        // Create and set date formatting
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper ch = workbook.getCreationHelper();
        dateStyle.setDataFormat(ch.createDataFormat().getFormat("mm/dd/yyyy"));
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setDefaultColumnStyle(5, dateStyle);

        // Iterate through the transferList and add each entry as an Excel row
        for (int i = 0; i < transferList.size(); i++) {
            row = sheet.createRow(i+1);
            resident = transferList.get(i);
            row.createCell(0).setCellValue(resident.getRefNo());
            row.createCell(1).setCellValue(resident.getLast());
            row.createCell(2).setCellValue(resident.getFirst());
            row.createCell(3).setCellValue(resident.getRes());
            row.createCell(4).setCellValue(resident.getInto());
            row.createCell(5).setCellValue(resident.getDate());
        }

        // Resize the columns
        for (int i = 0; i <= 26; i++) {
            sheet.autoSizeColumn(i);
        }

    }

    // gender(): get the correct gender index for the given character
    private int gender(String gender) {
        switch (gender) {
            case "F":
                return 1;
            case "M":
                return 2;
            default:
                return 3;
        }
    }

    // getDecline(): create a list of contracts and if they decline
    private List<Integer> getDecline(XSSFWorkbook workbook) {

        // Initiate declineList and add a 0 index
        List<Integer> declineList = new ArrayList<Integer>();
        declineList.add(0);

        // Get the CONTRACT sheet and get the first content row
        XSSFSheet sheet = workbook.getSheet("CONTRACT");
        int rowNo = 5;
        int rowBool;
        Row row = sheet.getRow(rowNo);

        // Get the "Declining? (Y/N)" column and add the values to their respective index
        while (row.getCell(5) != null) {
            rowBool = ((row.getCell(5).getStringCellValue().equals("Y")) ? 1 : 0);
            declineList.add(rowBool);
            rowNo++;
            row = sheet.getRow(rowNo);
        }

        // Return the completed list 
        return declineList;
    }

    // info(): display the information window
    @FXML
    private void info() {
        try {
            // Set the internal error message

            // Access the .fxml file for the dialog box
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation((App.class.getResource("info.fxml")));
            DialogPane errDialog = fxmlLoader.load();

            // Initialize the dialog box
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(errDialog);
            dialog.setTitle("Information - LifeCalc Transfer Table Generator");
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(App.class.getResourceAsStream("LC.png")));

            // Display the dialog box
            dialog.showAndWait();
            
        } catch (IOException e) {  
            // If exception, print message to status
            status.setText("DIALOG BOX ERROR: " + e.getMessage());
        }
    }
}
