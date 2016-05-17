package skanetrafikenAPI.downloaders;
/*
 * Created by jakkra
 */

public interface DataDownloadListener {
    void dataDownloadedSuccessfully(Object data);

    void dataDownloadFailed();
}