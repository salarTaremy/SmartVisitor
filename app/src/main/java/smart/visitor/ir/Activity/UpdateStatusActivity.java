package smart.visitor.ir.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Databases.CustomerDbHelper;
import Databases.CustomerGroupDbHelper;
import Databases.CustomerTypeIDbHelper;
import Databases.InventoryDbHelper;
import Databases.LinkPriceListCustomerDbhelper;
import Databases.PriceListDbHelpr;
import Databases.PriceListDetailIDbHelper;
import Databases.ProductBrandIDbHelper;
import Databases.ProductDbHelper;
import Databases.ProductGroupDbHelper;
import Databases.WarehouseDbHelper;
import Entities.Customer;
import Entities.CustomerGroup;
import Entities.CustomerType;
import Entities.FullDataViewModel;
import Entities.Inventory;
import Entities.LinkPriceListCustomer;
import Entities.PriceList;
import Entities.PriceListDetail;
import Entities.Product;
import Entities.ProductBrand;
import Entities.ProductGroup;
import Entities.Warehouse;
import Tools.Logcat;
import Tools.MessageBox;
import Tools.UnzipUtility;
import pl.droidsonroids.gif.GifImageView;
import smart.visitor.ir.R;

public class UpdateStatusActivity extends BaseActivity {
    GifImageView gif_updating , gif_updateConnecting,gif_updateDownloading,gif_updateSaving;
    TextView tv_updateConnecting,tv_updateDownloading,tv_updateSaving;
    TextView tv_PercentageDownloading,tv_PercentageSaving;
    TextView tv_UpdatingCaption,tv_UpdatingDetail;
    String Url ;
    String Ip ;
    String Port ;
    String Path ;
    String FileName = "Data.db";
    long Index = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        this.Url = getIntent().getStringExtra("Url");
        this.Ip = getIntent().getStringExtra("Ip");
        this.Port = getIntent().getStringExtra("Port");

        Path = getFilesDir().getPath() ;
        InitView();
        SetListener();
        StartUpdate();

    }

    private void StartUpdate() {
        DoStartUpdateEvent();

        int downloadId = PRDownloader.download(Url, Path, FileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Logcat.i("onStartOrResume");
                        gif_updateConnecting.setImageResource(R.drawable.success_gif);
                        gif_updateDownloading.setVisibility(View.VISIBLE);
                        tv_updateDownloading.setVisibility(View.VISIBLE);
                        tv_PercentageDownloading.setVisibility(View.VISIBLE);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        Logcat.i("onPause");
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Logcat.i("onCancel");
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        SetPercentage(progress.currentBytes ,progress.totalBytes  ,tv_PercentageDownloading );
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Logcat.i("onDownloadComplete" );
                        DoDownloadCompleteEvent();
                        SaveData();
                    }

                    @Override
                    public void onError(Error error) {
                        Logcat.e("error" );
                        String Msg = error.getConnectionException().getMessage();
                        Msg = Msg.replace(Ip,"Smart Visitor Server");
                        new MessageBox(context).ShowError("خطا در برقراری ارتباط با سرور",Msg);
                    }
                });
    }

    private void DoStartUpdateEvent() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_PercentageDownloading.setVisibility(View.INVISIBLE);
                        tv_PercentageSaving.setVisibility(View.INVISIBLE);
                        tv_updateDownloading.setVisibility(View.INVISIBLE);
                        tv_updateSaving.setVisibility(View.INVISIBLE);
                        gif_updateConnecting.setVisibility(View.VISIBLE);
                        tv_updateConnecting.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }

    private void DoDownloadCompleteEvent() {
        Thread t = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_updateDownloading.setImageResource(R.drawable.success_gif);
                        gif_updateSaving.setVisibility(View.VISIBLE);
                        tv_updateSaving.setVisibility(View.VISIBLE);
                        tv_PercentageSaving.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void DoSaveCompleteEvent() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gif_updateSaving.setImageResource(R.drawable.success_gif);
                        gif_updating.setImageResource(R.drawable.update_success);
                        tv_UpdatingCaption.setText(R.string.Success);
                        tv_UpdatingCaption.setTextColor(getResources().getColor(R.color.green));
                        tv_UpdatingDetail.setText(R.string.UpdateSuccess);
                        new MessageBox(context).ShowOk(  "به روز رسانی موفق"  , "عملیات با موفقیت به تمام رسید");
                        //setPictureInPictureParams(100,100,tv_updateSaving );
                    }
                });
            }
        }.start();
    }

    private void SaveData() {
        boolean FinishExtract = false;
        try {
            FinishExtract = new UnzipUtility().unzip( Path+"/"+ FileName,Path);
            if (FinishExtract){
                Logcat.i("FinishExtract");
                String json = RadJson();
                FullDataViewModel fullData = new Gson().fromJson(json, FullDataViewModel.class);
                InsertToDb(fullData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String RadJson() {

        String ret = "";
        InputStream inputStream = null;
        try {

            inputStream = openFileInput("Data.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Logcat.e( "File not found: " + e.toString());
        } catch (IOException e) {
            Logcat.e("Can not read file: " + e.toString());
        }
        finally {
            try {
                if ( inputStream != null ) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    private void SetListener() {

    }

    private void InitView() {
        this.gif_updating= findViewById(R.id.gif_updating);
        this.gif_updateConnecting= findViewById(R.id.gif_updateConnecting);
        this.gif_updateDownloading= findViewById(R.id.gif_updateDownloading);
        this.gif_updateSaving= findViewById(R.id.gif_updateSaving);
        this.tv_updateConnecting= findViewById(R.id.tv_updateConnecting);
        this.tv_updateDownloading= findViewById(R.id.tv_updateDownloading);
        this.tv_updateSaving= findViewById(R.id.tv_updateSaving);
        this.tv_PercentageDownloading= findViewById(R.id.tv_PercentageDownloading);
        this.tv_PercentageSaving= findViewById(R.id.tv_PercentageSaving);
        this.tv_UpdatingCaption= findViewById(R.id.tv_UpdatingCaption);
        this.tv_UpdatingDetail= findViewById(R.id.tv_UpdatingDetail);
    }

    private void SetPercentage(long Val , Long Max ,  final TextView tv){
        final Long Percent = Val * 100 / Max;
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String Txt =  Percent+ "%" ;
                        tv.setText(Txt);
                        Logcat.i(Txt);
                    }
                });
            }
        }.start();
    }
    private void SetPercentage( Long Max ,  final TextView tv){
        ++Index;
        final Long Percent = Index * 100 / Max;
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String Txt =  Percent+ "%" ;
                        tv.setText(Txt);
                    }
                });
            }
        }.start();
    }
    private void InsertToDb( final FullDataViewModel fullData) {
        long Cnt = 0 ;
        long I = 0 ;
        Cnt += fullData.ProductBrands.size();
        Cnt += fullData.ProductGroups.size();
        Cnt += fullData.Products.size();
        Cnt += fullData.CustomerTypes.size();
        Cnt += fullData.CustomerGroups.size();
        Cnt += fullData.Customers.size();
        Cnt += fullData.Warehouses.size();
        Cnt += fullData.inventories.size();
        Cnt += fullData.PriceLists.size();
        Cnt += fullData.LinkPriceListCustomer.size();
        for (PriceList p : fullData.PriceLists){
            Cnt += p.Details.size();
        }
        final long objectCount = Cnt;
        // ══════════════════ Define Db Helpers ═══════════════════════════
        final ProductBrandIDbHelper db_ProductBrands = new ProductBrandIDbHelper(context);
        final ProductGroupDbHelper db_ProductGroups = new ProductGroupDbHelper(context);
        final ProductDbHelper db_Products = new ProductDbHelper(context);
        final CustomerTypeIDbHelper db_CustomerTypes = new CustomerTypeIDbHelper(context);
        final CustomerGroupDbHelper db_CustomerGroups = new CustomerGroupDbHelper(context);
        final CustomerDbHelper db_Customers = new CustomerDbHelper(context);
        final WarehouseDbHelper db_Warehouses = new WarehouseDbHelper(context);
        final InventoryDbHelper db_inventories = new InventoryDbHelper(context);
        final PriceListDbHelpr db_Pr = new PriceListDbHelpr(context);
        final PriceListDetailIDbHelper db_Prd = new PriceListDetailIDbHelper(context);
        final LinkPriceListCustomerDbhelper db_Lpc = new LinkPriceListCustomerDbhelper(context);
        // ══════════════════ ProductBrands ═══════════════════════════
        SetPercentage(100L,100L,tv_PercentageDownloading);
        Thread T1 = new Thread (new Runnable() {
            public void run() {
                if (db_ProductBrands.delete()){
                    Logcat.i( "ProductBrand Delete All ");
                }
                for ( ProductBrand item: fullData.ProductBrands){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_ProductBrands.create(item)){
                        Logcat.i( "ProductBrand Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ ProductGroups ═══════════════════════════
                if (db_ProductGroups.delete()){
                    Logcat.i( "ProductGroups Delete All ");
                }
                for ( ProductGroup item: fullData.ProductGroups){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_ProductGroups.create(item)){
                        Logcat.i( "ProductGroup Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ Products ═══════════════════════════
                if (db_Products.delete()){
                    Logcat.i( "Products Delete All ");
                }
                for ( Product item: fullData.Products){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_Products.create(item)){
                        Logcat.i( "Product Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ CustomerTypes ═══════════════════════════
                if (db_CustomerTypes.delete()){
                    Logcat.i( "CustomerTypes Delete All ");
                }
                for ( CustomerType item: fullData.CustomerTypes){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_CustomerTypes.create(item)){
                        Logcat.i( "CustomerType Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ CustomerGroups ═══════════════════════════
                if (db_CustomerGroups.delete()){
                    Logcat.i( "CustomerGroups Delete All ");
                }
                for ( CustomerGroup item: fullData.CustomerGroups){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_CustomerGroups.create(item)){
                        Logcat.i( "CustomerGroup Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ Customers ═══════════════════════════
                if (db_Customers.delete()){
                    Logcat.i( "Customers Delete All ");
                }
                for ( Customer item: fullData.Customers){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_Customers.create(item)){
                        Logcat.i( "Customer Inserted: " + item.getName() );
                    }
                }
        // ══════════════════ Warehouses ═══════════════════════════
                if (db_Warehouses.delete()){
                    Logcat.i("Warehouses Delete All  "  );
                }
                for ( Warehouse item: fullData.Warehouses){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_Warehouses.create(item)){
                        Logcat.i( "Warehouses Inserted: " + item.Name );
                    }
                }
        // ══════════════════ inventories ═══════════════════════════
                if (db_inventories.delete()){
                    Logcat.i("inventories Delete All  "  );
                }
                for ( Inventory item: fullData.inventories){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_inventories.create(item)){
                        Logcat.i( "Inventory Inserted: " + String.valueOf(item.ID_Warehouse) );
                    }
                }
        // ══════════════════ PriceList ═══════════════════════════
                if (db_Pr.delete()){
                    Logcat.i("PriceList Delete All  "  );
                }
                if (db_Prd.delete()){
                    Logcat.i("PriceListDetail Delete All  "  );
                }
                for ( PriceList item: fullData.PriceLists){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_Pr.create(item)){
                        Logcat.i( "PriceList Inserted: " + item.Description );
                    }
                    for (PriceListDetail Dt: item.Details ) {
                        SetPercentage(objectCount,tv_PercentageSaving);
                        if (db_Prd.create(Dt)){
                            Logcat.i( "PriceListDetail Inserted: " + item.Description  +" => "+  Dt.ID);
                        }
                    }
                }
        // ══════════════════ LinkPriceListCustomer ═══════════════════════════
                if (db_Lpc.delete()){
                    Logcat.i("LinkPriceListCustomer Delete All  "  );
                }
                for ( LinkPriceListCustomer item: fullData.LinkPriceListCustomer){
                    SetPercentage(objectCount,tv_PercentageSaving);
                    if (db_Lpc.create(item)){
                        Logcat.i( "LinkPriceListCustomer Inserted: " );
                        DoSaveCompleteEvent();
                    }
                }
            }
        });
        T1.setName(db_ProductBrands.getClass().getName());
        T1.start();
        // ══════════════════ Finish Update ═══════════════════════════
    }
}