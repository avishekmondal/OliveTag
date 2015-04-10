package com.olivetag.database;

import java.util.ArrayList;
import com.olivetag.bin.CartItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	static final String KEY_CART_ID = "id";

	static final String KEY_PRODUCT_CODE = "product_code";

	static final String KEY_PRODUCT_NAME = "product_name";

	static final String KEY_PRODUCT_IMAGE = "product_image";

	static final String KEY_PRODUCT_PRICE = "product_price";

	static final String KEY_PRODUCT_BRAND = "product_brand";

	static final String KEY_PRODUCT_AVAILABILITY = "product_availability";

	static final String KEY_PRODUCT_DESCRIPTION = "product_description";

	static final String KEY_PRODUCT_CARE = "product_care";

	static final String KEY_PRODUCT_MATERIAL = "product_material";

	static final String KEY_PRODUCT_DELIVERY_TIME = "product_delivery_time";

	static final String KEY_PRODUCT_LIKE = "product_like";

	static final String KEY_PRODUCT_COMMENT = "product_comment";

	static final String KEY_PRODUCT_SIZE = "product_size";

	static final String KEY_PRODUCT_QUANTITY = "product_quantity";

	static final String TAG = "DBAdapter";

	static final String DATABASE_NAME = "CartListMasterDB";

	static final String DATABASE_TABLE = "cartlistmaster";

	static final int DATABASE_VERSION = 1;

	static final String DATABASE_CREATE = "create table cartlistmaster (id integer primary key autoincrement,"
			+ "product_code text not null, product_name text not null, product_image text not null, product_price text not null, product_brand text not null,product_availability text not null, product_description text not null, product_care text not null, product_material text not null, product_delivery_time text not null, product_like text not null, product_comment text not null, product_size text not null, product_quantity text not null);";

	final Context context;

	DatabaseHelper DBHelper;

	SQLiteDatabase db;

	String order_by = KEY_CART_ID;

	String[] col = new String[] { KEY_CART_ID, KEY_PRODUCT_CODE,
			KEY_PRODUCT_NAME, KEY_PRODUCT_IMAGE, KEY_PRODUCT_PRICE,
			KEY_PRODUCT_BRAND, KEY_PRODUCT_AVAILABILITY,
			KEY_PRODUCT_DESCRIPTION, KEY_PRODUCT_CARE, KEY_PRODUCT_MATERIAL,
			KEY_PRODUCT_DELIVERY_TIME, KEY_PRODUCT_LIKE, KEY_PRODUCT_COMMENT,
			KEY_PRODUCT_SIZE, KEY_PRODUCT_QUANTITY };

	ArrayList<CartItem> cartList = new ArrayList<CartItem>();

	public DBAdapter(Context ctx) {

		this.context = ctx;

		DBHelper = new DatabaseHelper(context);

	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {

			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			try {

				db.execSQL(DATABASE_CREATE);

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.v(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS cartlistmaster");

			onCreate(db);
		}

		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion,
				int newVersion) {

			Log.v(TAG, "Downgrading database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS cartlistmaster");

			onCreate(db);
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// ---opens the database---
	public DBAdapter open() throws SQLException {

		DBHelper = new DatabaseHelper(context);

		db = DBHelper.getWritableDatabase();

		return this;

	}

	// ---closes the database---
	public void close() {

		DBHelper.close();

	}

	// ---insert a contact into the database---
	public long insertValue(String product_code, String product_name,
			String product_image, String product_price, String product_brand,
			String product_availability, String product_description,
			String product_care, String product_material,
			String product_delivery_time, String product_like,
			String product_comment, String product_size, String product_quantity) {

		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_PRODUCT_CODE, product_code);

		initialValues.put(KEY_PRODUCT_NAME, product_name);

		initialValues.put(KEY_PRODUCT_IMAGE, product_image);

		initialValues.put(KEY_PRODUCT_PRICE, product_price);

		initialValues.put(KEY_PRODUCT_BRAND, product_brand);

		initialValues.put(KEY_PRODUCT_AVAILABILITY, product_availability);

		initialValues.put(KEY_PRODUCT_DESCRIPTION, product_description);

		initialValues.put(KEY_PRODUCT_CARE, product_care);

		initialValues.put(KEY_PRODUCT_MATERIAL, product_material);

		initialValues.put(KEY_PRODUCT_DELIVERY_TIME, product_delivery_time);

		initialValues.put(KEY_PRODUCT_LIKE, product_like);

		initialValues.put(KEY_PRODUCT_COMMENT, product_comment);

		initialValues.put(KEY_PRODUCT_SIZE, product_size);

		initialValues.put(KEY_PRODUCT_QUANTITY, product_quantity);

		return db.insert(DATABASE_TABLE, null, initialValues);

	}

	// ---deletes a particular contact---
	public boolean deleteRecord(String cartlist_id) {
		return db.delete(DATABASE_TABLE, KEY_CART_ID + "=" + cartlist_id, null) > 0;
	}

	// ---retrieves all records---

	public ArrayList<CartItem> getRecords() {
		// TODO Auto-generated method stub

		Cursor c = db.query(DATABASE_TABLE, col, null, null, null, null,
				order_by);

		int cartlist_id_pos = c.getColumnIndex(KEY_CART_ID);

		int product_code_pos = c.getColumnIndex(KEY_PRODUCT_CODE);

		int product_name_pos = c.getColumnIndex(KEY_PRODUCT_NAME);

		int product_image_pos = c.getColumnIndex(KEY_PRODUCT_IMAGE);

		int product_price_pos = c.getColumnIndex(KEY_PRODUCT_PRICE);

		int product_brand_pos = c.getColumnIndex(KEY_PRODUCT_BRAND);

		int product_availability_pos = c
				.getColumnIndex(KEY_PRODUCT_AVAILABILITY);

		int product_description_pos = c.getColumnIndex(KEY_PRODUCT_DESCRIPTION);

		int product_care_pos = c.getColumnIndex(KEY_PRODUCT_CARE);

		int product_material_pos = c.getColumnIndex(KEY_PRODUCT_MATERIAL);

		int product_delivery_time_pos = c
				.getColumnIndex(KEY_PRODUCT_DELIVERY_TIME);

		int product_like_pos = c.getColumnIndex(KEY_PRODUCT_LIKE);

		int product_comment_pos = c.getColumnIndex(KEY_PRODUCT_COMMENT);

		int product_size_pos = c.getColumnIndex(KEY_PRODUCT_SIZE);

		int product_quantity_pos = c.getColumnIndex(KEY_PRODUCT_QUANTITY);

		for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {

			String cartlist_id = c.getString(cartlist_id_pos);

			String product_code = c.getString(product_code_pos);

			String product_name = c.getString(product_name_pos);

			String product_image = c.getString(product_image_pos);

			String product_price = c.getString(product_price_pos);

			String product_brand = c.getString(product_brand_pos);

			String product_availability = c.getString(product_availability_pos);

			String product_description = c.getString(product_description_pos);

			String product_care = c.getString(product_care_pos);

			String product_material = c.getString(product_material_pos);

			String product_delivery_time = c
					.getString(product_delivery_time_pos);

			String product_like = c.getString(product_like_pos);

			String product_comment = c.getString(product_comment_pos);

			String product_size = c.getString(product_size_pos);

			String product_quantity = c.getString(product_quantity_pos);

			CartItem cartItem = new CartItem();

			// adding each child node to HashMap key => value

			cartItem.setCartId(cartlist_id);

			cartItem.setProductCode(product_code);

			cartItem.setProductName(product_name);

			cartItem.setProductImage(product_image);

			cartItem.setProductPrice(product_price);

			cartItem.setProductBrand(product_brand);

			cartItem.setProductAvailability(product_availability);

			cartItem.setProductDescription(product_description);

			cartItem.setProductCare(product_care);

			cartItem.setProductMaterial(product_material);

			cartItem.setProductDeliveryTime(product_delivery_time);

			cartItem.setProductLike(product_like);

			cartItem.setProductComment(product_comment);

			cartItem.setProductSize(product_size);

			cartItem.setProductQuantity(product_quantity);

			cartList.add(cartItem);
		}
		c.close();

		return cartList;

	}

}
