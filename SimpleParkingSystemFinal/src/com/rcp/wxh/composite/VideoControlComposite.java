package com.rcp.wxh.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import platedsp.MyDSP;

/**
 * 视频控制模块
 * 
 * @author wuxuehong
 *         2012-1-19
 */
public class VideoControlComposite extends Composite {

	// 所需要控制的视频模块
	private VideoComposite video;

	public VideoControlComposite(Composite parent, int style, VideoComposite vc) {
		super(parent, style);
		setLayout(new RowLayout(SWT.HORIZONTAL));
		video = vc;
		final MyDSP dsp = video.getDsp();
		final Button button = new Button(this, SWT.CHECK);// 开启视频
		button.setLayoutData(new RowData(SWT.DEFAULT, 25));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (video == null)
					return;
				if (button.getSelection()) {
					video.showVideo();
				} else {
					video.closeVideo();
				}
			}
		});
		button.setText("\u5F00\u542F\u89C6\u9891");

		// Button button_capture = new Button(this, SWT.NONE);
		// button_capture.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// // ReturnIntValue intValue = new ReturnIntValue();
		// // dsp.VideoGetOtherParams(def.DSP_VIDEO_BRIGHTNESS, intValue);
		// // dsp.VideoSetOtherParams(def.DSP_VIDEO_BRIGHTNESS, intValue.m_Value + 10);
		//
		// if (video!=null) {
		// video.captureImage("images/"+new Date().getTime()+".jpg");
		// }
		//
		// }
		// });
		// button_capture.setText("\u4FDD\u5B58\u622A\u56FE");

		final Combo combo = new Combo(this, SWT.NONE);
		combo.setLayoutData(new RowData(128, SWT.DEFAULT));

		int total = 4;
		for (int i = 0; i < total; i++) {
			dsp.VideoSetDeviceIndex(i);
			if (dsp.VideoGetDeviceName() != null && !("").equalsIgnoreCase(dsp.VideoGetDeviceName())) {
				combo.add(dsp.VideoGetDeviceName());
			}
		}
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dsp.VideoSetDeviceIndex(combo.getSelectionIndex());
//				combo.add(dsp.VideoGetDeviceName());
				System.out.println(dsp.VideoGetDeviceName());
			}
		});
	}
}
