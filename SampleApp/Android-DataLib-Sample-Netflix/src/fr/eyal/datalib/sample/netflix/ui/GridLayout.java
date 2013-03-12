/*
 * Copyright (C) 2012 Eyal LEZMY (http://www.eyal.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.eyal.datalib.sample.netflix.ui;

import fr.eyal.datalib.sample.netflix.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author Eyal LEZMY
 *
 */
public class GridLayout extends ViewGroup {

	private int mNumRows;
	private int mNumColumns;
	private float mPreferredCellWidth;
	private float mPreferredCellHeight;
	private float mMarginColumn;
	private float mMarginRow;
	
	public static class LayoutParams extends android.view.ViewGroup.LayoutParams {

		private int columnStart;
		private int rowStart;
		private int columnSpan;
		private int rowSpan;

		public LayoutParams(final Context c, final AttributeSet attrs) {
			super(10, 10);
			// get the layout attributes of it's child view from the xml layout file.
			TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.GridLayout_Layout);
			columnStart = a.getInt(R.styleable.GridLayout_Layout_layout_column, 0);
			rowStart = a.getInt(R.styleable.GridLayout_Layout_layout_row, 0);
			columnSpan = a.getInt(R.styleable.GridLayout_Layout_layout_columnSpan, 1);
			rowSpan = a.getInt(R.styleable.GridLayout_Layout_layout_rowSpan, 1);
			a.recycle();
		}

		public LayoutParams(final int columnStart, final int rowStart, final int columnSpan, final int rowSpan) {
			super(10, 10);
			this.columnStart = columnStart;
			this.rowStart = rowStart;
			this.columnSpan = columnSpan;
			this.rowSpan = rowSpan;
		}

		public int width() {
			return columnSpan;
		}

		public int height() {
			return rowSpan;
		}
	}

	public GridLayout(final Context context) {
		super(context);
	}

	public GridLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		readAttr(context, attrs);
	}

	private void readAttr(final Context c, final AttributeSet attrs) {
		TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.GridLayout);
		mNumRows = a.getInt(R.styleable.GridLayout_rows, 1);
		mNumColumns = a.getInt(R.styleable.GridLayout_columns, 1);
		mPreferredCellWidth = a.getDimension(R.styleable.GridLayout_preferredCellWidth, 1);
		mPreferredCellHeight = a.getDimension(R.styleable.GridLayout_preferredCellHeight, 1);
		mMarginColumn = a.getDimension(R.styleable.GridLayout_columnMargin, 0);
		mMarginRow = a.getDimension(R.styleable.GridLayout_rowMargin, 0);
		a.recycle();
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		//we get the prefered bound sizes
		float cellWidth = preferredCellWidth();
		float cellHeight = preferredCellHeight();
		
		//we compute the specific sizes
		if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
			float width = widthSize;
			width = width - getPaddingLeft() - getPaddingRight();
			width = width - mMarginColumn * (mNumColumns-1);
			width /= mNumColumns;
			if (widthMode == MeasureSpec.EXACTLY)
				cellWidth = width;
			else
				cellWidth = Math.min(cellWidth, width);
		}
		if (heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST) {
			float height = heightSize;
			height = height - getPaddingTop() - getPaddingBottom();
			height = height - mMarginRow * (mNumRows-1);
			height /= mNumRows;
			if (heightMode == MeasureSpec.EXACTLY)
				cellHeight = height;
			else
				cellHeight = Math.min(cellHeight, height);
		}

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				int width = Math.round(cellWidth * lp.columnSpan + mMarginColumn * (lp.columnSpan-1));
				int height = Math.round(cellHeight * lp.rowSpan + mMarginRow * (lp.rowSpan-1));
				child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
			}
		}

		int w = Math.round(cellWidth * mNumColumns + getPaddingLeft() + getPaddingRight() + mMarginColumn*(mNumColumns-1));
		int h = Math.round(cellHeight * mNumRows + getPaddingTop() + getPaddingBottom() + mMarginRow*(mNumRows-1));
		setMeasuredDimension(w, h);
	}


	@Override
	protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
		int width = r - l;
		int height = b - t;
		//		if (lastWidth == width && lastHeight == height)
		//			return;
		float cellWidth = width;
		float cellHeight = height;
		cellWidth = cellWidth - getPaddingLeft() - getPaddingRight();
		cellWidth = cellWidth - (mNumColumns-1) * mMarginColumn;
		cellHeight = cellHeight - getPaddingTop() - getPaddingBottom();
		cellHeight = cellHeight - (mNumRows-1) * mMarginRow;
		cellWidth /= mNumColumns;
		cellHeight /= mNumRows;
		int count = getChildCount();

		//		int posMin = mNumColumns;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child != null && child.getVisibility() != GONE) {
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				
				int cl = Math.round(getPaddingLeft() + lp.columnStart * (cellWidth + mMarginColumn));
				int ct = Math.round(getPaddingTop() + lp.rowStart * (cellHeight + mMarginRow));
				int cr = Math.round(getPaddingLeft() + (lp.columnSpan + lp.columnStart) * cellWidth + (lp.columnStart + lp.columnSpan - 1) * mMarginColumn);
				int cb = Math.round(getPaddingTop() + (lp.rowSpan + lp.rowStart) * cellHeight + (lp.rowStart + lp.rowSpan - 1) * mMarginRow);

				child.layout(cl, ct, cr, cb);

			}
		}
	}

	public android.view.ViewGroup.LayoutParams generateLayoutParams(
			final AttributeSet attrs) {
		// let the GridLayout.LayoutParams to read attributes of layout instead of ViewGroup.LayoutParams
		return new LayoutParams(getContext(), attrs);
	}

	protected boolean checkLayoutParams(final android.view.ViewGroup.LayoutParams p) {
		// if the layout params is invalid, the android will throw a runtime exception.
		if (p instanceof LayoutParams) {
			int columns = columns(), rows = rows();
			LayoutParams lp = (LayoutParams) p;
			if ((lp.columnStart+lp.columnSpan) > columns || lp.columnStart < 0)
				return false;
			if ((lp.rowStart+lp.rowSpan) > rows || lp.rowStart < 0)
				return false;
			return true;
		} else
			return false;
	}

	public void setNumRows(final int mNumRows) {
		this.mNumRows = mNumRows;
	}

	public void setNumColumns(final int nNumColumns) {
		mNumColumns = nNumColumns;
	}

	public int rows() {
		return mNumRows;
	}

	public int columns() {
		return mNumColumns;
	}

	public float marginRow() {
		return mMarginRow;
	}

	public float marginColumn() {
		return mMarginColumn;
	}

	public float preferredCellWidth() {
		return mPreferredCellWidth;
	}

	public float preferredCellHeight() {
		return mPreferredCellHeight;
	}



}
