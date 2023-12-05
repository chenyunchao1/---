  <template>
    <div class="song-list">
      <daohang/>
      <router-view class="music-content"/>
    </div>

</template>

<script>
import mixin from '../mixins'
import Daohang from "../components/Daohang";
import { mapGetters } from 'vuex'
import { HttpManager } from '../api/index'

export default {
  name: 'management',
  mixins: [mixin],
  components: {
    Daohang
  },

}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>
