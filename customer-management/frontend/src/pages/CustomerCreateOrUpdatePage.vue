<template>
  <div class="mt-4">
    <b-row>
      <b-col cols="3">
        <h2>{{ title }}</h2>
        <p>
          On this page you can create a customer record and edit it at the same time.
        </p>
      </b-col>
      <b-col cols="9">
        <div>
          <b-alert v-model="successful" variant="success" show>Customer successfully created!</b-alert>
          <b-alert v-model="error" variant="danger">
            {{errorMessage}}
          </b-alert>
        <label>Id Number:</label>
        <b-form-input
            id="idNumber"
            v-model="user.idNumber"
            :state="idNumberState"
            aria-describedby="input-live-help input-live-feedback"
            placeholder="Enter the ID Number"
            trim
        ></b-form-input>
        <b-form-invalid-feedback >
          You must enter a ID number of equals 11 characters!
        </b-form-invalid-feedback>
        </div>
        <b-row class="mt-3">
          <b-col cols="6">
            <label>First Name:</label>
            <b-form-input
                id="firstname"
                v-model="user.firstName"
                :state="nameState"
                aria-describedby="input-live-help input-live-feedback"
                placeholder="Enter the First Name"
                trim
            ></b-form-input>
            <b-form-invalid-feedback >
              You must enter a first name of at least two characters!
            </b-form-invalid-feedback>
          </b-col>
          <b-col cols="6">
            <label>Last Name:</label>
            <b-form-input
                id="lastname"
                v-model="user.lastName"
                :state="lastNameState"
                aria-describedby="input-live-help input-live-feedback"
                placeholder="Enter the last name"
                trim
            ></b-form-input>
            <b-form-invalid-feedback >
              You must enter a last name of at least two characters!
            </b-form-invalid-feedback>
          </b-col>
        </b-row>
        <div>
        <label class="mt-3">Email:</label>
        <b-form-input
            id="email"
            v-model="user.email"
            :state="emailState"
            aria-describedby="input-live-help input-live-feedback"
            placeholder="Enter the email"
            trim
        ></b-form-input>
        <b-form-invalid-feedback >
          You must enter a valid email!
        </b-form-invalid-feedback>
        </div>
        <div>
        <label class="mt-3">Phone:</label>
        <b-form-input
            id="phone"
            v-model="user.phone"
            :state="phoneState"
            aria-describedby="input-live-help input-live-feedback"
            placeholder="Enter the phone"
            trim
        ></b-form-input>
        <b-form-invalid-feedback >
          You must enter a valid phone!
        </b-form-invalid-feedback>
        </div>
        <b-row class="mt-3">
          <b-col cols="6">
            <label class="mr-2">City:</label>
            <b-form-select v-model="selectedCity" @change="getDistricts(null)" :options="cities" class="form-select"></b-form-select>
          </b-col>
          <b-col cols="6">
            <label class="mr-2">District:</label>
            <b-form-select v-model="selectedDistrict" :options="districts" class="form-select"></b-form-select>
          </b-col>
        </b-row>
        <div class="d-grid gap-2 mt-4">
          <b-button @click.stop="saveUser" :disabled="formValid()" variant="primary">Save User</b-button>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CustomerCreateOrUpdatePage",
  computed: {
    nameState() {
      if (this.user.firstName === '') return null;
      return this.user.firstName.length > 1;
    },
    lastNameState() {
      if (this.user.lastName === '') return null;
      return this.user.lastName.length > 1;
    },
    idNumberState() {
      if (this.user.idNumber === '') return null;
      return this.user.idNumber.length === 11;
    },
    emailState() {
      if (this.user.email === '') return null;
      return this.user.email.length > 3 && this.user.email.includes('@');
    },
   phoneState() {
      if (this.user.phone === '') return null;
      return this.user.phone.length > 10;
    },
  },
  data() {
    return {
      title: 'Customer Create',
      successful:false,
      error:false,
      errorMessage:'',
      user: {
        id: null,
        idNumber: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        city: null,
        district: null,
        nameState: true,
      },
      selectedCity: null,
      selectedDistrict: null,
      cities: [],
      districts: [
        { value: null, text: 'Before Select City' },
      ]
    };
  },
  methods:{
    formValid(){
      return !(this.user.firstName.length > 1 &&
          this.user.lastName.length > 1 &&
          this.user.idNumber.length === 11 &&
          this.user.email.length > 3 &&
          this.user.email.includes('@') &&
          this.user.phone.length > 10 &&
          this.selectedDistrict !== null &&
      this.selectedCity !== null);
    },
    getCities(){
      axios.get('/cities')
          .then((res)=>{
            this.cities.push({ value: null, text: 'Select City' });
            for (let i = 0; i < res.data.length; i++) {
              this.cities.push({value:res.data[i].cityId,text: res.data[i].city});
            }
          })
          .catch((err)=>{console.log(err)});
    },
    getDistricts(id){
      this.selectedDistrict = id;
      axios.get('/districts?cityId='+this.selectedCity)
          .then((res)=>{
            this.districts = [];
            this.districts.push({ value: null, text: 'Select District' });
            for (let i = 0; i < res.data.length; i++) {
              this.districts.push({value:res.data[i].districtId,text: res.data[i].district});
            }
          })
          .catch((err)=>{console.log(err)});
    },
    saveUser(){
      this.user.city = { cityId: this.selectedCity};
      this.user.district = { districtId: this.selectedDistrict};
      axios.post('/customers',this.user)
      .then(()=>{
        this.error =false;
        this.successful = true;
        let vm = this;
        setTimeout(function(){ vm.$router.push({name:'customers'}) }, 3000);
      })
      .catch((error)=>{
        this.error = true;
        this.errorMessage = error.response.data;
      });
    }
  },
  created() {
    if (this.$route.params.id){
      this.title = 'Customer Edit';
      axios.get('/customers/'+this.$route.params.id)
          .then((res)=>{
            this.user = res.data;
            this.selectedCity = res.data.city.cityId;
            this.getDistricts(res.data.district.districtId);
          })
          .catch(()=>{
            this.error = true;
            this.errorMessage ='User not found can create a new user';
            this.title = 'Customer Create';
          });
    }
    this.getCities();
  }
}
</script>

<style scoped>

</style>