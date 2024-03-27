import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ChartOptions, ChartType, ChartDataset} from 'chart.js/auto';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnChanges{

  @Input() consumptions: number[];

  public lineChartOptions: ChartOptions = {
    responsive: true,
  };

  public lineChartType: ChartType = 'line';
  public lineChartData: { datasets: ChartDataset<'line'>[], labels: string[] };
  public lineChartLegend = true;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['consumptions'] && this.consumptions) {
      this.lineChartData = {
        datasets: [
          {
            data: this.consumptions.sort((a, b) => a - b),
            label: 'Device consumption',
            borderColor: '#007bff',
            backgroundColor: 'rgba(0, 123, 255, 0.5)',
            fill: false,
          }
        ],
        labels: ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09']
      };
    }
  }

  // public lineChartType: ChartType = 'line'; // Define the chart type as 'line'
  //
  // // Data structure for Chart.js 3.x and ng2-charts 3.x
  // public lineChartData: { datasets: ChartDataset<'line'>[], labels: string[] } = {
  //   datasets: [
  //     {
  //       data: this.consumptions, // First line data
  //       label: 'Series A',
  //       borderColor: '#007bff',
  //       backgroundColor: 'rgba(0, 123, 255, 0.5)',
  //       fill: false,
  //     }
  //     // {
  //     //   data: [1.0, 2.1, 1.6, 1.8, 2.3], // Second line data
  //     //   label: 'Series B',
  //     //   borderColor: '#28a745',
  //     //   backgroundColor: 'rgba(40, 167, 69, 0.5)',
  //     //   fill: false,
  //     // }
  //   ],
  //   labels: ['00', '01', '02', '03', '04','05', '06', '07', '08', '09'] // These labels correspond to the data points
  // };
  //
  // public lineChartLegend = true;

}


