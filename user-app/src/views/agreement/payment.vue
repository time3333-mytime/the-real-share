<template>
  <div class="payment-container">
    <h1>支付宝支付</h1>
    <div v-if="error" class="error-message">{{ error }}</div>
    <img
      v-else-if="qrCodeData"
      :src="qrCodeData"
      alt="支付二维码"
      class="qrcode-image"
    />
    <div v-else class="loading">正在生成二维码...</div>
  </div>
</template>

<script>
import QRCode from "qrcode";

export default {
  data() {
    return {
      qrCodeData: null,
      error: ""
    };
  },
  mounted() {
    this.generateQRCode();
  },
  methods: {
    async generateQRCode() {
      try {
        const qrcodeUrl = this.$route.query.qrcodeUrl;
        if (!qrcodeUrl) {
          throw new Error("缺少二维码参数");
        }

        this.qrCodeData = await QRCode.toDataURL(qrcodeUrl, {
          width: 300,
          margin: 2,
          color: {
            dark: "#000000",
            light: "#ffffff"
          }
        });
      } catch (err) {
        this.error = "生成二维码失败：" + err.message;
      }
    }
  }
};
</script>

<style scoped>
.payment-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
}

.qrcode-image {
  width: 300px;
  height: 300px;
  border: 1px solid #eee;
  padding: 10px;
  margin-top: 20px;
}

.error-message {
  color: red;
  margin: 20px 0;
}

.loading {
  margin: 20px 0;
  color: #666;
}
</style>
