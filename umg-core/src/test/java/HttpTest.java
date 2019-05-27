import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @Author: finleyli
 * @Date: Created in 2019/2/15
 * @Describe:
 **/
public class HttpTest {
    public static void main(String[] args) throws Exception {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(20);
        connectionManager.setDefaultMaxPerRoute(10);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(1000)
                .build();
//
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(null, new TrustAllStrategy()).build();

        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLContext(sslContext)
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpGet get = new HttpGet("https://www.baidu.com");
        CloseableHttpResponse response = httpClient.execute(get);
//        String str = IOUtils.toString(response.getEntity().getContent(),"UTF-8");
//        System.out.println(str);

        response.close();
        httpClient.close();
    }
}
