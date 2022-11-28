<template>
  <div style="text-align: center; width: 800px; height: 400px; margin-top: 20px">
    <div class="column" style="height: 400px; width: 800px; margin-right: 0">
      <b-form-checkbox-group
        v-model="selected"
        style="column-count: 6"
        inline
        :options="options"
        class="mb-3"
        value-field="item"
        text-field="name"
        :aria-describedby="ariaDescribedby"
      ></b-form-checkbox-group>
      <canvas id="myChart" width="300" height="130"></canvas>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const safetyStore = "safetyStore";
import Chart from "chart.js/auto";

export default {
  name: "LineChart",
  data() {
    return {
      selected: ["trffac", "fire", "crim", "natdsast", "comu_safe", "infect"],
      options: [
        { item: "trffac", name: "교통" },
        { item: "fire", name: " 화재" },
        { item: "crim", name: " 범죄" },
        { item: "natdsast", name: " 자연재해" },
        { item: "comu_safe", name: " 생활안전" },
        { item: "infect", name: " 감염병" },
      ],
      data: {
        labels: ["서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"],
        datasets: [
          {
            label: "지역안전지수",
            data: [2, 2.7, 2.5, 3.2, 2.3, 3.2, 1.8, 2.3, 1.2, 3.2, 2.5, 3, 2, 3.3, 2.5, 2.2, 2.7],
            fill: false,
            // borderColor: "rgba(0, 156, 207, 1)",
            borderColor: "rgba(114, 196, 244, 1)",
            tension: 0.1,
            borderWidth: 5,
          },
        ],
      },
    };
  },
  computed: {
    ...mapState(safetyStore, ["safetys"]),
  },
  methods: {
    ...mapActions(safetyStore, ["getSafetyList"]),

    // addData() {
    //   this.dataset.push(this.dataentry);
    //   this.labels.push(this.datalabel);
    //   this.datalabel = "";
    //   this.dataentry = "";
    // },

    loadChart() {
      const ctx = document.getElementById("myChart");

      const myChart = new Chart(ctx, {
        type: "line",
        data: this.data,
        options: {
          scales: {
            y: {
              beginAtZero: false,
              reverse: true,
            },
          },
        },
        plugins: {
          legend: {
            position: "bottom",
            align: "start",
          },
        },
      });
      myChart;
    },
  },
  created() {
    this.getSafetyList();
  },
  watch: {
    selected: function () {
      // this.realData.splice(0);
      console.log("clicked");
      // console.log(this.data.datasets[0].data);
      console.log(this.data.datasets[0].data);
      this.data.datasets[0].data.splice(0);
      console.log(this.data.datasets[0].data);

      this.loadChart();
    },
  },
  mounted() {
    this.loadChart();
  },
  // mounted() {
  //   const ctx = document.getElementById("myChart");

  //   const myChart = new Chart(ctx, {
  //     type: "line",
  //     data: this.data,
  //     options: {
  //       scales: {
  //         y: {
  //           beginAtZero: false,
  //           reverse: true,
  //         },
  //       },
  //     },
  //     plugins: {
  //       legend: {
  //         position: "bottom",
  //         align: "start",
  //       },
  //     },
  //   });
  //   myChart;
  // },
};
</script>

<style></style>
