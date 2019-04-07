import Chart from 'chart.js';
import { COLORS } from '../../constants/colors';

export default (function () {
  // ------------------------------------------------------
  // @Line Charts
  // ------------------------------------------------------

  const lineChartBox = document.getElementById('line-chart');

  if (lineChartBox) {
    const lineCtx = lineChartBox.getContext('2d');
    lineChartBox.height = 80;

    new Chart(lineCtx, {
      type: 'line',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
          label                : 'Chandler, AZ',
          backgroundColor      : 'rgba(237, 231, 246, 0.5)',
          borderColor          : COLORS['deep-purple-500'],
          pointBackgroundColor : COLORS['deep-purple-700'],
          borderWidth          : 2,
          data                 : [60, 50, 70, 60, 50, 70, 60],
        }, {
          label                : 'Phoenix, AZ',
          backgroundColor      : 'rgba(232, 245, 233, 0.5)',
          borderColor          : COLORS['blue-500'],
          pointBackgroundColor : COLORS['blue-700'],
          borderWidth          : 2,
          data                 : [70, 75, 85, 70, 75, 85, 70],
        }],
      },

      options: {
        legend: {
          display: false,
        },
        plugins: {
          datalabels: {
             display: true,
             align: 'center',
             anchor: 'center'
          }
       }
      },

    });
  }

  // ------------------------------------------------------
  // @Bar Charts - 1
  // ------------------------------------------------------

  const barChartBox1 = document.getElementById('bar-chart-1');

  if (barChartBox1) {
    const barCtx = barChartBox1.getContext('2d');

    new Chart(barCtx, {
      type: 'bar',
      data: {
        labels: ['Sugar Level'],
        datasets: [{
          label           : 'Hardware',
          backgroundColor : COLORS['light-purple-500'],
          borderColor     : COLORS['light-purple-800'],
          borderWidth     : 1,
          data            : [96903],
        }, {
          label           : 'Warehouse',
          backgroundColor : COLORS['light-blue-500'],
          borderColor     : COLORS['light-blue-800'],
          borderWidth     : 1,
          data            : [320785],
        },{
          label           : 'Inventory',
          backgroundColor : COLORS['light-green-500'],
          borderColor     : COLORS['light-green-800'],
          borderWidth     : 1,
          data            : [116307.3],
        }],
      },

      options: {
        responsive: true,
        legend: {
          position: 'bottom',
        },
      },
    });
  }

  // ------------------------------------------------------
  // @Bar Charts - 2
  // ------------------------------------------------------

  const barChartBox2 = document.getElementById('bar-chart-2');

  if (barChartBox2) {
    const barCtx = barChartBox2.getContext('2d');

    new Chart(barCtx, {
      type: 'bar',
      data: {
        labels: ['Electroencephalogram'],
        datasets: [{
          label           : 'Hardware',
          backgroundColor : COLORS['light-purple-500'],
          borderColor     : COLORS['light-purple-800'],
          borderWidth     : 1,
          data            : [11495.77],
        }, {
          label           : 'Warehouse',
          backgroundColor : COLORS['light-blue-500'],
          borderColor     : COLORS['light-blue-800'],
          borderWidth     : 1,
          data            : [23636.09],
        },{
          label           : 'Inventory',
          backgroundColor : COLORS['light-green-500'],
          borderColor     : COLORS['light-green-800'],
          borderWidth     : 1,
          data            : [16740.86],
        }],
      },

      options: {
        responsive: true,
        legend: {
          position: 'bottom',
        },
      },
    });
  }

  // ------------------------------------------------------
  // @Bar Charts - 3
  // ------------------------------------------------------

  const barChartBox3 = document.getElementById('bar-chart-3');

  if (barChartBox3) {
    const barCtx = barChartBox3.getContext('2d');

    new Chart(barCtx, {
      type: 'bar',
      data: {
        labels: ['Blood pressure'],
        datasets: [{
          label           : 'Hardware',
          backgroundColor : COLORS['light-purple-500'],
          borderColor     : COLORS['light-purple-800'],
          borderWidth     : 1,
          data            : [179],
        }, {
          label           : 'Warehouse',
          backgroundColor : COLORS['light-blue-500'],
          borderColor     : COLORS['light-blue-800'],
          borderWidth     : 1,
          data            : [251],
        },{
          label           : 'Inventory',
          backgroundColor : COLORS['light-green-500'],
          borderColor     : COLORS['light-green-800'],
          borderWidth     : 1,
          data            : [220],
        }],
      },

      options: {
        responsive: true,
        legend: {
          position: 'bottom',
        },
      },
    });
  }

  // ------------------------------------------------------
  // @Bar Charts - 4
  // ------------------------------------------------------

  const barChartBox4 = document.getElementById('bar-chart-4');

  if (barChartBox4) {
    const barCtx = barChartBox4.getContext('2d');

    new Chart(barCtx, {
      type: 'bar',
      data: {
        labels: ['Heart Rate'],
        datasets: [{
          label           : 'Hardware',
          backgroundColor : COLORS['light-purple-500'],
          borderColor     : COLORS['light-purple-800'],
          borderWidth     : 1,
          data            : [635],
        }, {
          label           : 'Warehouse',
          backgroundColor : COLORS['light-blue-500'],
          borderColor     : COLORS['light-blue-800'],
          borderWidth     : 1,
          data            : [1066],
        },{
          label           : 'Inventory',
          backgroundColor : COLORS['light-green-500'],
          borderColor     : COLORS['light-green-800'],
          borderWidth     : 1,
          data            : [836],
        }],
      },

      options: {
        responsive: true,
        legend: {
          position: 'bottom',
        },
      },
    });
  }

  // ------------------------------------------------------
  // @Area Charts
  // ------------------------------------------------------

  const areaChartBox = document.getElementById('area-chart');

  if (areaChartBox) {
    const areaCtx = areaChartBox.getContext('2d');

    new Chart(areaCtx, {
      type: 'line',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [{
          backgroundColor : 'rgba(3, 169, 244, 0.5)',
          borderColor     : COLORS['light-blue-800'],
          data            : [10, 50, 20, 40, 60, 30, 70],
          label           : 'Dataset',
          fill            : 'start',
        }],
      },
    });
  }

  // ------------------------------------------------------
  // @Scatter Charts
  // ------------------------------------------------------

  const scatterChartBox = document.getElementById('scatter-chart');

  if (scatterChartBox) {
    const scatterCtx = scatterChartBox.getContext('2d');

    Chart.Scatter(scatterCtx, {
      data: {
        datasets: [{
          label           : 'My First dataset',
          borderColor     : COLORS['red-500'],
          backgroundColor : COLORS['red-500'],
          data: [
            { x: 10, y: 20 },
            { x: 30, y: 40 },
            { x: 50, y: 60 },
            { x: 70, y: 80 },
            { x: 90, y: 100 },
            { x: 110, y: 120 },
            { x: 130, y: 140 },
          ],
        }, {
          label           : 'My Second dataset',
          borderColor     : COLORS['green-500'],
          backgroundColor : COLORS['green-500'],
          data: [
            { x: 150, y: 160 },
            { x: 170, y: 180 },
            { x: 190, y: 200 },
            { x: 210, y: 220 },
            { x: 230, y: 240 },
            { x: 250, y: 260 },
            { x: 270, y: 280 },
          ],
        }],
      },
    });
  }
}())
