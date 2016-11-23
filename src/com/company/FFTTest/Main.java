

import com.company.SignalReader;
import com.company.SineExample;
import org.jtransforms.fft.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class Main {

    static JFreeChart chart;

    static int i = 0;

    public static void main(String[] args) {

        double[] sine = SineExample.getSine();
        //show(sine, "Sine");

        double[] sineSpectrum = sine.clone();
        DoubleFFT_1D fftDo = new DoubleFFT_1D(sineSpectrum.length);
        fftDo.realForward(sineSpectrum);



        show(sineSpectrum, "Sinus Spectrum");

        while (true) {

            i++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double[] arr = new double[sineSpectrum.length];

            for(int j = 0; j < sineSpectrum.length; j++) {
                arr[j] = i;
            }

            XYDataset ds = createDataset(arr, 25000);

            chart = ChartFactory.createXYLineChart("q",
                    "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                    false);

        }

    }

    private static void show(double[] array, String Title) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Charts");

                frame.setSize(1000, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                XYDataset ds = createDataset(array, 25000);
                chart = ChartFactory.createXYLineChart(Title,
                        "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                        false);

                ChartPanel cp = new ChartPanel(chart);


                frame.getContentPane().add(cp);
            }
        });

    }

    private static XYDataset createDataset(double[] array, double SampleRate) {

        DefaultXYDataset ds = new DefaultXYDataset();

        double[][] data = new double[2][array.length];


        for (int i = 0; i < array.length; i++) {
            data[0][i] = ((double)i * SampleRate / 2) / (array.length);
            data[1][i] = array[i];
        }

        ds.addSeries("signal", data);

        return ds;
    }


}
