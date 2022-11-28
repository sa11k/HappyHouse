<template>
  <section class="container">
    <!-- <h1>Demo examples of vue-charts</h1>
    <div class="columns">
      <div class="column">
        <h3>Line Chart</h3>
        <chartjs-line></chartjs-line>
      </div>
      <div class="column">
        <h3>Bar Chart</h3>
        <chartjs-bar></chartjs-bar>
      </div>
    </div>
    <div class="columns">
      <div class="column">
        <h3>Radar Chart</h3>
        <chartjs-radar></chartjs-radar>
      </div>
      <div class="column">
        <h3>Data Binding Line Chart</h3>
        <form @submit.prevent="addData">
          <input placeholder="Add a Data" v-model="dataentry" />
          <input placeholder="Add a Label" v-model="datalabel" />
          <button type="submit">Submit</button>
        </form>
        <chartjs-line :labels="labels" :data="dataset" :bind="true"></chartjs-line>
      </div>
    </div> -->
    <div class="column">
      <b-form-checkbox-group v-model="selected" :options="options" class="mb-3" value-field="item" text-field="name" disabled-field="notEnabled"></b-form-checkbox-group>
    </div>
    <div class="chartbox" style="height: 90px">
      <chartjs-line :labels="labels" :data="datasets.data" :title="title" :bind="true"></chartjs-line>
    </div>
  </section>
</template>

<script>
import { mapState, mapActions } from "vuex";
const safetyStore = "safetyStore";

export default {
  name: "VueCharts",
  data() {
    return {
      selected: ["trffac", "fire", "crim", "natdsast", "comu_safe", "infect"],
      options: [
        { item: "trffac", name: "교통" },
        { item: "fire", name: "화재" },
        { item: "crim", name: "범죄" },
        { item: "natdsast", name: "자연재해" },
        { item: "comu_safe", name: "생활안전" },
        { item: "infect", name: "감염병" },
        // { item: { d: 1 }, name: "Option D" },
      ],
      labels: ["서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"],
      datasets: {
        data: [65, 59, 80, 81, 56, 55, 40, 65, 59, 80, 81, 56, 55, 40, 30, 42, 15],
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
  },
  created() {
    this.getSafetyList();
    this.datasets.data.splice(0);
    this.safetys.forEach((element) => {
      var avg = 0;
      avg = avg + element.comu_safe;
      avg = avg + element.crim;
      avg = avg + element.fire;
      avg = avg + element.infect;
      avg = avg + element.natdsast;
      avg = avg + element.trffac;
      console.log("fire ", typeof element.fire);
      console.log("infect ", typeof element.infect);
      console.log("natdsast ", typeof element.natdsast);
      console.log("traffac ", typeof element.traffac);
      avg = avg / 6;
      console.log("avg : ", avg);
      this.datasets.data.push(avg);
    });
  },
  watch: {
    selected: function () {
      this.datasets.data.splice(0);
    },
  },
};
</script>

<style>
.chartbox {
  display: block;
  height: 300px;
  width: 600px;
}
</style>
