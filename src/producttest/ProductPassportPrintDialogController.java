/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import producttest.model.ProductPassport;

/**
 * FXML Controller class
 *
 * @author tonkushin
 */
public class ProductPassportPrintDialogController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnPrinterSettings;
    @FXML
    private Button btnPageSettings;

    WebEngine webEngine;

    PrinterJob job;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = webView.getEngine();
    }

    public void initData(ProductPassport pp) {
        String format = "<html><head>\n"
                + "<style type=\"text/css\">\n"
                + "		.mainTable {width: 100%%; border-collapse: collapse; border: 1px solid #000000; font-size: 12px;} \n"
                + "		.mainTable td{border: 1px solid #000000; padding:3px; font-size: 12px;} \n"
                + "		.centered-td {border: 1px solid #000000; padding:3px; font-size: 12px; text-align: center;} \n"
                + "               </style>"
                + "</head>\n"
                + "<body style=\"font-family: Arial;font-size: 12px;\">\n"
                + " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" summary=\"\" width=\"100%%\" style=\"font-family: 'Arial'; font-size: 12px; \">\n"
                + "        <tr>\n"
                + "            <td style=\"text-align: center\" width=\"49%%\">\n"
                + "                <span style=\"margin-left: 16px;\">\n"
                + "                    <table>\n"
                + "                        <tr>\n"
                + "                        <td> <img src=\"http://192.168.10.41:9090/apex/wwv_flow_file_mgr.get_file?p_security_group_id=2996626322616727&p_flow_id=444&p_fname=logo.png\" alt=\"\" class=\"appIcon #REGION_STATIC_ID#\" width=\"100px\" style=\"vertical-align: middle; float: left;\"/>\n"
                + "                        </td>\n"
                + "                            <td style=\"vertical-align: middle; text-align: center; font-size: 12px;\">\n"
                + "                            %s<br />\n"
                + "                %s<br />\n"
                + "                %s<br />\n"
                + "                %s\n"
                + "                        </td>\n"
                + "                        </tr>\n"
                + "                    </table>\n"
                + "                <strong>ПАСПОРТ № %s</strong>\n"
                + "                <br />\n"
                + "                <p style=\"text-align: left\">\n"
                + "                    Наименование: Изделия стеновые неармированные из ячеистого бетона <br />автоклавного \n"
                + "                    твердения ГОСТ 31360-2007</p>\n"
                + "                <p><em>%s</em></p>\n"
                + "                <p style=\"text-align: left\">\n"
                + "                    Назначение: конструкционно-теплоизоляционные<br />\n"
                + "                    Внутреннее обозначение: %s</p>\n"
                + "                <table align=\"left\" width=\"100%%\">\n"
                + "                    <tr>\n"
                + "                        <td style=\"font-size: 12px; text-align: left\" width=\"50%%\">\n"
                + "                            Номер парти %s</td>\n"
                + "                        <td style=\"font-size: 12px; text-align: left\">\n"
                + "                            Объём продукции, м<sup>3</sup></td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td style=\"font-size: 12px; text-align: left\" width=\"50%%\">\n"
                + "                            Дата выпуска %s</td>\n"
                + "                        <td style=\"font-size: 12px; text-align: left\">\n"
                + "                            Дата отгрузки</td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                <p>&nbsp;</p>\n"
                + "                <p style=\"text-align: left; font-size: x-small;\">Условное обозначение: Блок I/%d х %d х %d/D%d/%s/%s ГОСТ 31360-2007</p>\n"
                + "                <table class='mainTable'>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" class='centered-td'>\n"
                + "                            <strong>Основные показатели</strong></td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            <strong>Результаты испытаний</strong></td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td rowspan=\"3\" style=\"text-align: left; vertical-align: middle;\">\n"
                + "                            Геометрические размеры, мм</td>\n"
                + "                        <td style=\"text-align: left\">\n"
                + "                            длина</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td style=\"text-align: left\">\n"
                + "                            толщина</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td style=\"text-align: left\">\n"
                + "                            высота</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td style=\"text-align: left\" colspan=\"2\">\n"
                + "                            Предел прочности при сжатии, кгс/см<sup>2</sup></td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d (%s)</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Требуемая прочность, кгс/см<sup>2</sup></td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Средняя плотность, кг/м<sup>3</sup> (марка)</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %d (D%d)</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\" class=\"style2\">\n"
                + "                            Коэффициент паропроницаемости, мг/м ч Па</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %.2f</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Марка по морозостойкости</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %s</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Коэффициент теплопроводности в сухом состоянии, Вт/(м <sup>0</sup>С)</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %.3f</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Усадка при высыхании, мм/м</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %.2f</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td colspan=\"2\" style=\"text-align: left\">\n"
                + "                            Удельная эффективная активность ЕРН, Бк/кг</td>\n"
                + "                        <td class='centered-td'>\n"
                + "                            %s</td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                <!--<p style=\"text-align: left; font-size: x-small;\">Санитарно-эпидемиологическое заключение \n"
                + "                    №66.01.32.147.П.000005.01.09</p>\n"
                + "                <p style=\"text-align: left; font-size: x-small;\"><sup>*</sup> - марка по морозостойкости определена по ГОСТ 25485-89 \n"
                + "                    (приложение 3)</p>-->\n"
                + "                    <br />\n"
                + "                <table width=\"100%%\">\n"
                + "                    <tr>\n"
                + "                        <td style=\"text-align: left; padding:3px; font-size: 12px;\">\n"
                + "                            %s</td>\n"
                + "                        <td style=\"text-align: right; padding:3px; font-size: 12px;\">\n"
                + "                            %s</td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <!--<td style=\"text-align: left; padding:3px;\">\n"
                + "                            Старший контролёр</td>\n"
                + "                        <td style=\"text-align: right; padding:3px;\">\n"
                + "                            &P618_SENIOR_CTRL.</td>-->\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                <!-- <p style=\"text-align: right\">&P618_DATE.</p> -->\n"
                + "                    <td style=\"text-align: center\" width=\"49%%\">\n"
                + "            </td>\n"
                + "        </tr>\n"
                + "    </table>"
                + "</body>"
                + "</html>";

        //Для правильного форматирования даты.
        DateFormat df = new SimpleDateFormat("dd.MM.YYYY");
        
        //Загружаем настройки
        Config cfg = new Config();
        cfg.readPropertiesFromFile();

        /**
         * 1.Наименование предприятия.
         * 2. Адрес.
         * 3. Телефон/факс
         * 4. Адрес в сети интернет
         * 
         * 5. Номер партии. 6. Наименование продукции. 7. Номер партии. 8. Длина
         * блока. 9. Ширина блока. 10. Высота блока. 11.плотность блока. 12. Марка
         * по прочности. 13. Марка по морозостойкости. 14. Длина блока. 15.
         * Ширина блока. 16. Высота блока. 17. Предел прочности при сжатии. 
         * 18.Марка по плотности 19. Коэффициент паропроницаемости. 20. Марка по
         * морозостойкости. 21. Коэффициент теплопроводности. 22. Усадка при
         * высыхании. 23. Удельная эффективная активность. 24. Профессия подписывающего.
         * 25. ФИО.
         */
        String html = String.format(format, cfg.getHeaderLine1(), cfg.getHeaderLine2(), 
                cfg.getHeaderLine3(), cfg.getHeaderLine4(), pp.getPartNum(), cfg.getHeaderProductName(),
                pp.getProduct().getName(), pp.getPartNum(), df.format(pp.getDate()),
                pp.getProduct().getLength(), pp.getProduct().getWidth(),
                pp.getProduct().getHeight(), pp.getProduct().getDensity(),
                pp.getDurabilityMark(), pp.getFrostResist(),
                pp.getProduct().getLength(), pp.getProduct().getWidth(),
                pp.getProduct().getHeight(), Math.round(pp.getAvgDurability()), pp.getDurabilityMark(),
                Math.round(pp.getReqDurability()), Math.round(pp.getAvgDensity()), pp.getProduct().getDensity(),
                pp.getSteamFactor(), pp.getFrostResist(), pp.getHeatConduction(), 
                pp.getShrinkage(), pp.getActivity(), cfg.getFooterProfession(),
                cfg.getFooterPersonName());
        
        webEngine.loadContent(html);

        job = PrinterJob.createPrinterJob();
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        job.getJobSettings().setPageLayout(pageLayout);
    }

    @FXML
    private void btnPrintOnClick(ActionEvent event) {
        //Отправляем на печать.
        if (job != null) {
            webEngine.print(job);
            job.endJob();
        }
        
        //Закрываем окно.
        Stage stage = (Stage) btnPrint.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnPrinterSettingsOnClick(ActionEvent event) {
        job.showPrintDialog(null);
    }

    @FXML
    private void btnPageSettingsOnClick(ActionEvent event) {
        job.showPageSetupDialog(null);
    }
}
