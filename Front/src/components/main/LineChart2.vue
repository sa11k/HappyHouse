<template>
  <div style="text-align: center; width: 800px; height: 400px; margin-top: 20px">
    <div class="column" style="height: 400px; width: 800px; margin-right: 0">
      <b-form-checkbox-group
        v-model="selected"
        style="column-count: 6"
        inline
        :options="option"
        class="mb-3"
        value-field="item"
        text-field="name"
        :aria-describedby="ariaDescribedby"
      ></b-form-checkbox-group>
      <canvas ref="lineChart" width="300" height="130" />
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const safetyStore = "safetyStore";
// import { Chart, registerables } from "chart.js";
// Chart.register(...registerables);
import Chart from "chart.js/auto";
let chart;

export default {
  name: "LineChart2",
  data() {
    return {
      selected: ["trffac", "fire", "crim", "natdsast", "comu_safe", "infect"],
      option: [
        { item: "trffac", name: "교통" },
        { item: "fire", name: " 화재" },
        { item: "crim", name: " 범죄" },
        { item: "natdsast", name: " 자연재해" },
        { item: "comu_safe", name: " 생활안전" },
        { item: "infect", name: " 감염병" },
      ],
      type: "line",
      data: {
        //chartData
        labels: ["서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"],
        datasets: [
          {
            label: "지역안전지수",
            data: [2, 2.7, 2.5, 3.2, 2.3, 3.2, 1.8, 2.3, 1.2, 3.2, 2.5, 3, 2, 3.3, 2.5, 2.2, 2.7],
            fill: false,
            // backgroundColor: ["rgba(255, 99, 132, 0.2)", "rgba(54, 162, 235, 0.2)", "rgba(255, 206, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(153, 102, 255, 0.2)", "rgba(255, 159, 64, 0.2)"],
            // borderColor: ["rgba(255, 99, 132, 1)", "rgba(54, 162, 235, 1)", "rgba(255, 206, 86, 1)", "rgba(75, 192, 192, 1)", "rgba(153, 102, 255, 1)", "rgba(255, 159, 64, 1)"],
            borderColor: "rgba(114, 196, 244, 1)",
            tension: 0.1,
            borderWidth: 5,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: false,
            reverse: true,
          },
        },
      },
    };
  },
  computed: {
    ...mapState(safetyStore, ["safetys"]),
  },
  watch: {
    selected: function () {
      this.createChart();
    },
    // deep: true
  },
  async mounted() {
    this.$nextTick(function () {
      this.createChart();
    });
  },
  methods: {
    ...mapActions(safetyStore, ["getSafetyList"]),

    createChart() {
      if (chart != undefined) {
        chart.destroy();
      }
      chart = new Chart(this.$refs.lineChart, {
        type: "line",
        data: this.data,
        options: this.options,
        plugins: {
          legend: {
            position: "bottom",
            align: "start",
          },
        },
      });
      // console.log(this.selected);
      // chart.data.datasets[0].data.splice(0);
      // var newData = [];
      // console.log("safetys :", this.safetys);
      // for (var i = 0; i < this.safetys.lenth; i++) {
      //   var newValue = 0;
      //   var Object = Object.keys(this.safetys[i]);
      //   for (var j = 0; j < Object.length; j++) {
      //     for (var k = 0; k < this.selected.length; k++) {
      //       var title = this.selected[k];

      //       if (Object[j] == title) {
      //         newValue += this.safetys[i].title;
      //       }
      //     }
      //   }
      //   newData.push(newValue / this.selected.length);
      // }
      // chart.data.datasets[0].data = newData;
      // chart.update();
    },
  },
};
</script>

<style></style>
