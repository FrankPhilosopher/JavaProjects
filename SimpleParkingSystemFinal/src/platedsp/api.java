// Don't edit this file !!!!!!!
// This is auto make.

package platedsp;

/**
 * api基本上就是和platedsp.java.dll相对应的类，全是native方法
 * platedsp.java.dll利用了platedsp.api.dll
 */
class api {
	static {
		System.loadLibrary("platedsp.java");
	}

	public native String dspGetVersion(int Type);

	public native byte[] dspGetByteArray(long pData, int Size);

	public native long dspCreate(String pGuiName);

	public native int dspDestroy(long hdsp);

	public native int dspSetSyncEventCallback(long hdsp, Object pObj);

	public native int dspResetImageDisplayWindow(long hdsp, Object awtWindow);

	public native int dspDoEvent(long hdsp);

	public native int dspImageGetIdentity(long hdsp);

	public native int dspImageGetByField(long hdsp);

	public native int dspImageSetByField(long hdsp, int Params);

	public native int dspImageGetDisplayEnabled(long hdsp);

	public native int dspImageSetDisplayEnabled(long hdsp, int Params);

	public native int dspImageStreamCopy(long hdsp, byte[] pBuf, int Params);

	public native int dspImageIsBest(long hdsp);

	public native int dspImagePlateIsBest(long hdsp);

	public native int dspImageSetCompressQuality(long hdsp, int Quality);

	public native int dspImageStreamSave(long hdsp, String FileName, int Params);

	public native int dspImageStreamSaveEx(long hdsp, String FileName, byte[] pStream, int Params);

	public native int dspBufferImageStream(long hdsp, int Id);

	public native int dspBufferCopy(long hdsp, byte[] pDesBuf, int Id, int Params);

	public native int dspBufferSave(long hdsp, int Id, int Params, String FileName, int bWaitFinished);

	public native int dspImageStreamPlateCopy(long hdsp, byte[] pBuf, int Params);

	public native int dspImageStreamPlateSave(long hdsp, String FileName, int Params);

	public native int dspImageStreamSetTitle(long hdsp, int x, int y, String Msg);

	public native int dspSetRedBoxDisplayEnabled(long hdsp, int Params);

	public native int dspSetRecogRangeBoxDisplayEnabled(long hdsp, int Params);

	public native int dspGetPlateBrightness(long hdsp);

	public native int dspGetPlateColor(long hdsp);

	public native String dspGetColorName(long hdsp, int Color);

	public native int dspGetPlateCount(long hdsp);

	public native int dspGetPlateSpeed(long hdsp, Object px, Object py);

	public native int dspGetPlateLocatedRect(long hdsp, ReturnIntValue pLeft, ReturnIntValue pTop,
			ReturnIntValue pRight, ReturnIntValue pBottom);

	public native String dspGetPlateNumber(long hdsp);

	public native int dspGetPlateReliability(long hdsp);

	public native byte[] dspGetPlateReliabilityByChar(long hdsp);

	public native String dspGetPlateTypeName(long hdsp);

	public native String dspGetTaxiTypeName(long hdsp);

	public native int dspGetCarColor(long hdsp);

	public native int dspGetRecogCarColorEnabled(long hdsp);

	public native int dspSetRecogCarColorEnabled(long hdsp, int bEnabled);

	public native int dspRecogCfgGetMinReliability(long hdsp);

	public native int dspRecogCfgSetMinReliability(long hdsp, int Params);

	public native String dspRecogCfgGetProvince(long hdsp);

	public native int dspRecogCfgSetProvince(long hdsp, String Province);

	public native int dspRecogCfgGetPlateRange(long hdsp, ReturnIntValue pMinWidth, ReturnIntValue pMinHeight,
			ReturnIntValue pMaxWidth, ReturnIntValue pMaxHeight);

	public native int dspRecogCfgSetPlateRange(long hdsp, int MinWidth, int MinHeight, int MaxWidth, int MaxHeight);

	public native int dspRecogCfgGetImageRange(long hdsp, ReturnIntValue pLeft, ReturnIntValue pTop,
			ReturnIntValue pRight, ReturnIntValue pBottom);

	public native int dspRecogCfgSetImageRange(long hdsp, int Left, int Top, int Right, int Bottom);

	public native int dspRecogCfgGetUseTemplate(long hdsp);

	public native int dspRecogCfgSetUseTemplate(long hdsp, int Params);

	public native int dspRecogParamDlg(long hdsp);

	public native int dspRecogTrainDlg(long hdsp);

	public native int dspRecogGetTick(long hdsp);

	public native int dspRecogGetEnableCount(long hdsp);

	public native int dspRecogSetEnableCount(long hdsp, int Count);

	public native int dspRecogSetMaxTimeCheckEnabled(long hdsp, int Params);

	public native int dspRecogStartWithFile(long hdsp, String FileName, int Params);

	public native int dspRecogStartWithMem(long hdsp, Object pData, int Size, int Params);

	public native int dspSetRecogImageFormat4Mem(long hdsp, int Format, int Width, int Height, int Params);

	public native int dspSetRecogBitmapInfo4Mem(long hdsp, int BitCount, int Compression, int Width, int Height,
			int Params);

	public native int dspWaitRecogFinished(long hdsp, int Tick);

	public native int dspWaitFileSaved(long hdsp, int Tick);

	public native int dspAviGetCurrentPosition(long hdsp);

	public native int dspAviSetCurrentPosition(long hdsp, int Pos);

	public native int dspAviGetDuration(long hdsp);

	public native int dspAviSetFrameStep(long hdsp, int Frames);

	public native int dspAviIsFinished(long hdsp);

	public native int dspAviStart(long hdsp, String FileName);

	public native int dspAviPause(long hdsp);

	public native int dspAviStop(long hdsp);

	public native int dspDvrGetBufferFrameNum(long hdsp);

	public native int dspDvrSetBufferFrameNum(long hdsp, int FrameNum);

	public native int dspDvrSetUseHalfX(long hdsp, int Params);

	public native int dspDvrCompressDlg(long hdsp);

	public native int dspDvrGetCompressor(long hdsp);

	public native int dspDvrSetCompressor(long hdsp, int Compressor);

	public native String dspDvrGetCompressorDes(long hdsp);

	public native int dspDvrGetCurrentPosition(long hdsp);

	public native int dspDvrGetFrameStep(long hdsp);

	public native int dspDvrSetFrameStep(long hdsp, int Step);

	public native int dspDvrStart(long hdsp, String FileName);

	public native int dspDvrStop(long hdsp, String FileName, int Params);

	public native int dspDvrSetTitle(long hdsp, int x, int y, String Msg);

	public native int dspDvrImageCopy(long hdsp, byte[] pDesBuf, int Num, int bCircumgyrate90);

	public native int dspDvrImageBuffer(long hdsp, int SrcLoopBufIndex, int DesId);

	public native int dspVideoGetCaptureSize(long hdsp, Object pWidth, Object pHeight);

	public native int dspVideoSetCaptureSize(long hdsp, int Width, int Height);

	public native int dspVideoGetConnected(long hdsp);

	public native int dspVideoSetConnected(long hdsp, int Params);

	public native int dspVideoGetDeviceIndex(long hdsp);

	public native int dspVideoSetDeviceIndex(long hdsp, int Index);

	public native String dspVideoGetDeviceName(long hdsp);

	public native int dspVideoDisplayDlg(long hdsp);

	public native int dspVideoGetDisplayFormat(long hdsp);

	public native int dspVideoSetDisplayFormat(long hdsp, int Params);

	public native int dspVideoFormatDlg(long hdsp);

	public native int dspVideoGetSource(long hdsp);

	public native int dspVideoSetSource(long hdsp, int Source);

	public native int dspVideoSourceDlg(long hdsp);

	public native int dspVideoGetOtherParams(long hdsp, int Type, ReturnIntValue Ret);

	public native int dspVideoSetOtherParams(long hdsp, int Type, int Params);

	public native int dspVideoReadIO(long hdsp, int Type, byte[] pBuf);

	public native int dspVideoWriteIO(long hdsp, int Type, byte[] pData, int Size);

	public native int dspMsgInfoGetEnabled(long hdsp);

	public native int dspMsgInfoSetEnabled(long hdsp, int Params);

	public native int dspMsgInfoDisplay(long hdsp, String Msg);

	public native String dspMsgLogoImageGetFile(long hdsp);

	public native int dspMsgLogoImageSetFile(long hdsp, String FileName);

	public native int dspMsgLogoImageRefresh(long hdsp);

	public native int dspMsgEnabledFPS(long hdsp, int bEnabled);

	public native int dspLicenseDataRead(long hdsp, int FileID, byte[] Password, byte[] pBuf);

	public native int dspLicenseDataWrite(long hdsp, int FileID, byte[] Password, byte[] pSrc);

	public native int dspLicenseGetUserSerialID(long hdsp, byte[] Password);

	public native int dspLicensePasswordChange(long hdsp, int FileID, byte[] OldPassword, byte[] NewPassword);

	public native int dspStatClear(long hdsp);

	public native int dspStatGetColorUsed(long hdsp);

	public native int dspStatSetColorUsed(long hdsp, int Params);

	public native int dspStatGetEnabled(long hdsp);

	public native int dspStatSetEnabled(long hdsp, int Params);

	public native int dspStatInsert(long hdsp, int Color, String Number);

	public native int dspStatGetMaxTime(long hdsp);

	public native int dspStatSetMaxTime(long hdsp, int Tick);

	public native int dspStatPasteBestRecord(long hdsp, int Params);

	public native int dspStatSetMaxTargetNum(long hdsp, int Num);

	public native int dspStatSetCurrentTarget(long hdsp, int Type, int Id);

	public native int dspMotionSetRunMode(long hdsp, int RunMode);

	public native int dspMotionSetDisplayResultEnabled(long hdsp, int Params);

	public native int dspMotionSetDisplayLaneEnabled(long hdsp, int Params);

	public native int dspMotionGetLaneNum(long hdsp);

	public native int dspMotionSetLaneNum(long hdsp, int Num);

	public native int dspMotionSetLaneRangeY(long hdsp, int Top, int Bottom);

	public native int dspMotionGetLaneRangeY(long hdsp, ReturnIntValue pTop, ReturnIntValue pBottom);

	public native int dspMotionSetLaneRangeX(long hdsp, int LaneIndex, int TopLeft, int TopRight, int BottomLeft,
			int BottomRight);

	public native int dspMotionGetLaneRangeX(long hdsp, int LaneIndex, ReturnIntValue pTopLeft,
			ReturnIntValue pTopRight, ReturnIntValue pBottomLeft, ReturnIntValue pBottomRight);

	public native int dspMotionGetCurrentLocatedRect(long hdsp, int MotionTarget, ReturnIntValue pLeft,
			ReturnIntValue pTop, ReturnIntValue pRight, ReturnIntValue pBottom);

	public native int dspMotionGetIdentity(long hdsp, int MotionTarget);

	public native int dspMotionGetIdentityByPlateTarget(long hdsp, int Target);

	public native int dspMotionGetCurrentLaneIndex(long hdsp, int MotionTarget);

	public native int dspMotionGetResultType(long hdsp, int MotionTarget);

	public native int dspMotionGetSpeedX(long hdsp, int MotionTarget);

	public native int dspMotionGetSpeedY(long hdsp, int MotionTarget);

	public native int dspMotionCfgLoad(long hdsp);

	public native int dspMotionCfgSave(long hdsp);

	public native int dspRedLampDetectSetEnabled(long hdsp, int Params);

	public native int dspRedLampDetectGetNum(long hdsp);

	public native int dspRedLampDetectSetNum(long hdsp, int Num);

	public native int dspRedLampDetectSetDisplayResultEnabled(long hdsp, int Params);

	public native int dspRedLampDetectSetDisplayRangeEnabled(long hdsp, int Params);

	public native int dspRedLampDetectSetMinDots(long hdsp, int DotNum);

	public native int dspRedLampDetectGetMinDots(long hdsp);

	public native int dspRedLampDetectSetRecogRange(long hdsp, int LampIndex, int Left, int Top, int Right, int Bottom);

	public native int dspRedLampDetectGetRecogRange(long hdsp, int LampIndex, ReturnIntValue pLeft,
			ReturnIntValue pTop, ReturnIntValue pRight, ReturnIntValue pBottom);

	public native int dspRedLampDetectGetLocate(long hdsp, int LampIndex, ReturnIntValue pLeft, ReturnIntValue pTop,
			ReturnIntValue pRight, ReturnIntValue pBottom);

	public native int dspRedLampDetectCfgLoad(long hdsp);

	public native int dspRedLampDetectCfgSave(long hdsp);
}
