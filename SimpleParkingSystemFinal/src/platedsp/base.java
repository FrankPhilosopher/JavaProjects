// Don't edit this file !!!!!!!
// This is auto make.

package platedsp;

/*
 * 继承自api，对于一些方法有自己的实现
 */
public class base extends api {
	base() {
		m_hdsp = 0L;// private long m_hdsp;
	}

	public String GetVersion(int Type) {
		return dspGetVersion(Type);
	}

	public byte[] GetByteArray(long pData, int Size) {
		return dspGetByteArray(pData, Size);
	}

	// 创建
	public boolean Create(String pGuiName) {
		if (m_hdsp == 0L)
			m_hdsp = dspCreate(pGuiName);// 默认是0L，创建一个窗体，成功返回一个非0值
		return (m_hdsp != 0L);// 不等于（创建成功）就返回true
	}

	// 销毁
	public void Destroy() {
		if (m_hdsp != 0L)
			dspDestroy(m_hdsp);// 非0值表示存在，需要销毁
		m_hdsp = 0L;
	}

	// 设置回调函数
	public int SetSyncEventCallback(Object pObj) {
		return dspSetSyncEventCallback(m_hdsp, pObj);
	};

	public int ResetImageDisplayWindow(Object awtWindow) {
		return dspResetImageDisplayWindow(m_hdsp, awtWindow);
	};

	public int DoEvent() {
		return dspDoEvent(m_hdsp);
	};

	public int ImageGetIdentity() {
		return dspImageGetIdentity(m_hdsp);
	};

	public int ImageGetByField() {
		return dspImageGetByField(m_hdsp);
	};

	public int ImageSetByField(int Params) {
		return dspImageSetByField(m_hdsp, Params);
	};

	public int ImageGetDisplayEnabled() {
		return dspImageGetDisplayEnabled(m_hdsp);
	};

	public int ImageSetDisplayEnabled(int Params) {
		return dspImageSetDisplayEnabled(m_hdsp, Params);
	};

	public int ImageStreamCopy(byte[] pBuf, int Params) {
		return dspImageStreamCopy(m_hdsp, pBuf, Params);
	};

	public int ImageIsBest() {
		return dspImageIsBest(m_hdsp);
	};

	public int ImagePlateIsBest() {
		return dspImagePlateIsBest(m_hdsp);
	};

	public int ImageSetCompressQuality(int Quality) {
		return dspImageSetCompressQuality(m_hdsp, Quality);
	};

	public int ImageStreamSave(String FileName, int Params) {
		return dspImageStreamSave(m_hdsp, FileName, Params);
	};

	public int ImageStreamSaveEx(String FileName, byte[] pStream, int Params) {
		return dspImageStreamSaveEx(m_hdsp, FileName, pStream, Params);
	};

	public int BufferImageStream(int Id) {
		return dspBufferImageStream(m_hdsp, Id);
	};

	public int BufferCopy(byte[] pDesBuf, int Id, int Params) {
		return dspBufferCopy(m_hdsp, pDesBuf, Id, Params);
	};

	public int BufferSave(int Id, int Params, String FileName, int bWaitFinished) {
		return dspBufferSave(m_hdsp, Id, Params, FileName, bWaitFinished);
	};

	public int ImageStreamPlateCopy(byte[] pBuf, int Params) {
		return dspImageStreamPlateCopy(m_hdsp, pBuf, Params);
	};

	public int ImageStreamPlateSave(String FileName, int Params) {
		return dspImageStreamPlateSave(m_hdsp, FileName, Params);
	};

	public int ImageStreamSetTitle(int x, int y, String Msg) {
		return dspImageStreamSetTitle(m_hdsp, x, y, Msg);
	};

	public int SetRedBoxDisplayEnabled(int Params) {
		return dspSetRedBoxDisplayEnabled(m_hdsp, Params);
	};

	public int SetRecogRangeBoxDisplayEnabled(int Params) {
		return dspSetRecogRangeBoxDisplayEnabled(m_hdsp, Params);
	};

	public int GetPlateBrightness() {
		return dspGetPlateBrightness(m_hdsp);
	};

	public int GetPlateColor() {
		return dspGetPlateColor(m_hdsp);
	};

	public String GetColorName(int Color) {
		return dspGetColorName(m_hdsp, Color);
	};

	public int GetPlateCount() {
		return dspGetPlateCount(m_hdsp);
	};

	public int GetPlateSpeed(Object px, Object py) {
		return dspGetPlateSpeed(m_hdsp, px, py);
	};

	public int GetPlateLocatedRect(ReturnIntValue pLeft, ReturnIntValue pTop, ReturnIntValue pRight,
			ReturnIntValue pBottom) {
		return dspGetPlateLocatedRect(m_hdsp, pLeft, pTop, pRight, pBottom);
	};

	public String GetPlateNumber() {
		return dspGetPlateNumber(m_hdsp);
	};

	public int GetPlateReliability() {
		return dspGetPlateReliability(m_hdsp);
	};

	public byte[] GetPlateReliabilityByChar() {
		return dspGetPlateReliabilityByChar(m_hdsp);
	};

	public String GetPlateTypeName() {
		return dspGetPlateTypeName(m_hdsp);
	};

	public String GetTaxiTypeName() {
		return dspGetTaxiTypeName(m_hdsp);
	};

	public int GetCarColor() {
		return dspGetCarColor(m_hdsp);
	};

	public int GetRecogCarColorEnabled() {
		return dspGetRecogCarColorEnabled(m_hdsp);
	};

	public int SetRecogCarColorEnabled(int bEnabled) {
		return dspSetRecogCarColorEnabled(m_hdsp, bEnabled);
	};

	public int RecogCfgGetMinReliability() {
		return dspRecogCfgGetMinReliability(m_hdsp);
	};

	public int RecogCfgSetMinReliability(int Params) {
		return dspRecogCfgSetMinReliability(m_hdsp, Params);
	};

	public String RecogCfgGetProvince() {
		return dspRecogCfgGetProvince(m_hdsp);
	};

	public int RecogCfgSetProvince(String Province) {
		return dspRecogCfgSetProvince(m_hdsp, Province);
	};

	public int RecogCfgGetPlateRange(ReturnIntValue pMinWidth, ReturnIntValue pMinHeight, ReturnIntValue pMaxWidth,
			ReturnIntValue pMaxHeight) {
		return dspRecogCfgGetPlateRange(m_hdsp, pMinWidth, pMinHeight, pMaxWidth, pMaxHeight);
	};

	public int RecogCfgSetPlateRange(int MinWidth, int MinHeight, int MaxWidth, int MaxHeight) {
		return dspRecogCfgSetPlateRange(m_hdsp, MinWidth, MinHeight, MaxWidth, MaxHeight);
	};

	public int RecogCfgGetImageRange(ReturnIntValue pLeft, ReturnIntValue pTop, ReturnIntValue pRight,
			ReturnIntValue pBottom) {
		return dspRecogCfgGetImageRange(m_hdsp, pLeft, pTop, pRight, pBottom);
	};

	public int RecogCfgSetImageRange(int Left, int Top, int Right, int Bottom) {
		return dspRecogCfgSetImageRange(m_hdsp, Left, Top, Right, Bottom);
	};

	public int RecogCfgGetUseTemplate() {
		return dspRecogCfgGetUseTemplate(m_hdsp);
	};

	public int RecogCfgSetUseTemplate(int Params) {
		return dspRecogCfgSetUseTemplate(m_hdsp, Params);
	};

	public int RecogParamDlg() {
		return dspRecogParamDlg(m_hdsp);
	};

	public int RecogTrainDlg() {
		return dspRecogTrainDlg(m_hdsp);
	};

	public int RecogGetTick() {
		return dspRecogGetTick(m_hdsp);
	};

	public int RecogGetEnableCount() {
		return dspRecogGetEnableCount(m_hdsp);
	};

	public int RecogSetEnableCount(int Count) {
		return dspRecogSetEnableCount(m_hdsp, Count);
	};

	public int RecogSetMaxTimeCheckEnabled(int Params) {
		return dspRecogSetMaxTimeCheckEnabled(m_hdsp, Params);
	};

	public int RecogStartWithFile(String FileName, int Params) {
		return dspRecogStartWithFile(m_hdsp, FileName, Params);
	};

	public int RecogStartWithMem(Object pData, int Size, int Params) {
		return dspRecogStartWithMem(m_hdsp, pData, Size, Params);
	};

	public int SetRecogImageFormat4Mem(int Format, int Width, int Height, int Params) {
		return dspSetRecogImageFormat4Mem(m_hdsp, Format, Width, Height, Params);
	};

	public int SetRecogBitmapInfo4Mem(int BitCount, int Compression, int Width, int Height, int Params) {
		return dspSetRecogBitmapInfo4Mem(m_hdsp, BitCount, Compression, Width, Height, Params);
	};

	public int WaitRecogFinished(int Tick) {
		return dspWaitRecogFinished(m_hdsp, Tick);
	};

	public int WaitFileSaved(int Tick) {
		return dspWaitFileSaved(m_hdsp, Tick);
	};

	public int AviGetCurrentPosition() {
		return dspAviGetCurrentPosition(m_hdsp);
	};

	public int AviSetCurrentPosition(int Pos) {
		return dspAviSetCurrentPosition(m_hdsp, Pos);
	};

	public int AviGetDuration() {
		return dspAviGetDuration(m_hdsp);
	};

	public int AviSetFrameStep(int Frames) {
		return dspAviSetFrameStep(m_hdsp, Frames);
	};

	public int AviIsFinished() {
		return dspAviIsFinished(m_hdsp);
	};

	public int AviStart(String FileName) {
		return dspAviStart(m_hdsp, FileName);
	};

	public int AviPause() {
		return dspAviPause(m_hdsp);
	};

	public int AviStop() {
		return dspAviStop(m_hdsp);
	};

	public int DvrGetBufferFrameNum() {
		return dspDvrGetBufferFrameNum(m_hdsp);
	};

	public int DvrSetBufferFrameNum(int FrameNum) {
		return dspDvrSetBufferFrameNum(m_hdsp, FrameNum);
	};

	public int DvrSetUseHalfX(int Params) {
		return dspDvrSetUseHalfX(m_hdsp, Params);
	};

	public int DvrCompressDlg() {
		return dspDvrCompressDlg(m_hdsp);
	};

	public int DvrGetCompressor() {
		return dspDvrGetCompressor(m_hdsp);
	};

	public int DvrSetCompressor(int Compressor) {
		return dspDvrSetCompressor(m_hdsp, Compressor);
	};

	public String DvrGetCompressorDes() {
		return dspDvrGetCompressorDes(m_hdsp);
	};

	public int DvrGetCurrentPosition() {
		return dspDvrGetCurrentPosition(m_hdsp);
	};

	public int DvrGetFrameStep() {
		return dspDvrGetFrameStep(m_hdsp);
	};

	public int DvrSetFrameStep(int Step) {
		return dspDvrSetFrameStep(m_hdsp, Step);
	};

	public int DvrStart(String FileName) {
		return dspDvrStart(m_hdsp, FileName);
	};

	public int DvrStop(String FileName, int Params) {
		return dspDvrStop(m_hdsp, FileName, Params);
	};

	public int DvrSetTitle(int x, int y, String Msg) {
		return dspDvrSetTitle(m_hdsp, x, y, Msg);
	};

	public int DvrImageCopy(byte[] pDesBuf, int Num, int bCircumgyrate90) {
		return dspDvrImageCopy(m_hdsp, pDesBuf, Num, bCircumgyrate90);
	};

	public int DvrImageBuffer(int SrcLoopBufIndex, int DesId) {
		return dspDvrImageBuffer(m_hdsp, SrcLoopBufIndex, DesId);
	};

	public int VideoGetCaptureSize(Object pWidth, Object pHeight) {
		return dspVideoGetCaptureSize(m_hdsp, pWidth, pHeight);
	};

	public int VideoSetCaptureSize(int Width, int Height) {
		return dspVideoSetCaptureSize(m_hdsp, Width, Height);
	};

	public int VideoGetConnected() {
		return dspVideoGetConnected(m_hdsp);
	};

	public int VideoSetConnected(int Params) {
		return dspVideoSetConnected(m_hdsp, Params);
	};

	public int VideoGetDeviceIndex() {
		return dspVideoGetDeviceIndex(m_hdsp);
	};

	public int VideoSetDeviceIndex(int Index) {
		return dspVideoSetDeviceIndex(m_hdsp, Index);
	};

	public String VideoGetDeviceName() {
		return dspVideoGetDeviceName(m_hdsp);
	};

	public int VideoDisplayDlg() {
		return dspVideoDisplayDlg(m_hdsp);
	};

	public int VideoGetDisplayFormat() {
		return dspVideoGetDisplayFormat(m_hdsp);
	};

	public int VideoSetDisplayFormat(int Params) {
		return dspVideoSetDisplayFormat(m_hdsp, Params);
	};

	public int VideoFormatDlg() {
		return dspVideoFormatDlg(m_hdsp);
	};

	public int VideoGetSource() {
		return dspVideoGetSource(m_hdsp);
	};

	public int VideoSetSource(int Source) {
		return dspVideoSetSource(m_hdsp, Source);
	};

	public int VideoSourceDlg() {
		return dspVideoSourceDlg(m_hdsp);
	};

	public int VideoGetOtherParams(int Type, ReturnIntValue Ret) {
		return dspVideoGetOtherParams(m_hdsp, Type, Ret);
	};

	public int VideoSetOtherParams(int Type, int Params) {
		return dspVideoSetOtherParams(m_hdsp, Type, Params);
	};

	public int VideoReadIO(int Type, byte[] pBuf) {
		return dspVideoReadIO(m_hdsp, Type, pBuf);
	};

	public int VideoWriteIO(int Type, byte[] pData, int Size) {
		return dspVideoWriteIO(m_hdsp, Type, pData, Size);
	};

	public int MsgInfoGetEnabled() {
		return dspMsgInfoGetEnabled(m_hdsp);
	};

	public int MsgInfoSetEnabled(int Params) {
		return dspMsgInfoSetEnabled(m_hdsp, Params);
	};

	public int MsgInfoDisplay(String Msg) {
		return dspMsgInfoDisplay(m_hdsp, Msg);
	};

	public String MsgLogoImageGetFile() {
		return dspMsgLogoImageGetFile(m_hdsp);
	};

	public int MsgLogoImageSetFile(String FileName) {
		return dspMsgLogoImageSetFile(m_hdsp, FileName);
	};

	public int MsgLogoImageRefresh() {
		return dspMsgLogoImageRefresh(m_hdsp);
	};

	public int MsgEnabledFPS(int bEnabled) {
		return dspMsgEnabledFPS(m_hdsp, bEnabled);
	};

	public int LicenseDataRead(int FileID, byte[] Password, byte[] pBuf) {
		return dspLicenseDataRead(m_hdsp, FileID, Password, pBuf);
	};

	public int LicenseDataWrite(int FileID, byte[] Password, byte[] pSrc) {
		return dspLicenseDataWrite(m_hdsp, FileID, Password, pSrc);
	};

	public int LicenseGetUserSerialID(byte[] Password) {
		return dspLicenseGetUserSerialID(m_hdsp, Password);
	};

	public int LicensePasswordChange(int FileID, byte[] OldPassword, byte[] NewPassword) {
		return dspLicensePasswordChange(m_hdsp, FileID, OldPassword, NewPassword);
	};

	public int StatClear() {
		return dspStatClear(m_hdsp);
	};

	public int StatGetColorUsed() {
		return dspStatGetColorUsed(m_hdsp);
	};

	public int StatSetColorUsed(int Params) {
		return dspStatSetColorUsed(m_hdsp, Params);
	};

	public int StatGetEnabled() {
		return dspStatGetEnabled(m_hdsp);
	};

	public int StatSetEnabled(int Params) {
		return dspStatSetEnabled(m_hdsp, Params);
	};

	public int StatInsert(int Color, String Number) {
		return dspStatInsert(m_hdsp, Color, Number);
	};

	public int StatGetMaxTime() {
		return dspStatGetMaxTime(m_hdsp);
	};

	public int StatSetMaxTime(int Tick) {
		return dspStatSetMaxTime(m_hdsp, Tick);
	};

	public int StatPasteBestRecord(int Params) {
		return dspStatPasteBestRecord(m_hdsp, Params);
	};

	public int StatSetMaxTargetNum(int Num) {
		return dspStatSetMaxTargetNum(m_hdsp, Num);
	};

	public int StatSetCurrentTarget(int Type, int Id) {
		return dspStatSetCurrentTarget(m_hdsp, Type, Id);
	};

	public int MotionSetRunMode(int RunMode) {
		return dspMotionSetRunMode(m_hdsp, RunMode);
	};

	public int MotionSetDisplayResultEnabled(int Params) {
		return dspMotionSetDisplayResultEnabled(m_hdsp, Params);
	};

	public int MotionSetDisplayLaneEnabled(int Params) {
		return dspMotionSetDisplayLaneEnabled(m_hdsp, Params);
	};

	public int MotionGetLaneNum() {
		return dspMotionGetLaneNum(m_hdsp);
	};

	public int MotionSetLaneNum(int Num) {
		return dspMotionSetLaneNum(m_hdsp, Num);
	};

	public int MotionSetLaneRangeY(int Top, int Bottom) {
		return dspMotionSetLaneRangeY(m_hdsp, Top, Bottom);
	};

	public int MotionGetLaneRangeY(ReturnIntValue pTop, ReturnIntValue pBottom) {
		return dspMotionGetLaneRangeY(m_hdsp, pTop, pBottom);
	};

	public int MotionSetLaneRangeX(int LaneIndex, int TopLeft, int TopRight, int BottomLeft, int BottomRight) {
		return dspMotionSetLaneRangeX(m_hdsp, LaneIndex, TopLeft, TopRight, BottomLeft, BottomRight);
	};

	public int MotionGetLaneRangeX(int LaneIndex, ReturnIntValue pTopLeft, ReturnIntValue pTopRight,
			ReturnIntValue pBottomLeft, ReturnIntValue pBottomRight) {
		return dspMotionGetLaneRangeX(m_hdsp, LaneIndex, pTopLeft, pTopRight, pBottomLeft, pBottomRight);
	};

	public int MotionGetCurrentLocatedRect(int MotionTarget, ReturnIntValue pLeft, ReturnIntValue pTop,
			ReturnIntValue pRight, ReturnIntValue pBottom) {
		return dspMotionGetCurrentLocatedRect(m_hdsp, MotionTarget, pLeft, pTop, pRight, pBottom);
	};

	public int MotionGetIdentity(int MotionTarget) {
		return dspMotionGetIdentity(m_hdsp, MotionTarget);
	};

	public int MotionGetIdentityByPlateTarget(int Target) {
		return dspMotionGetIdentityByPlateTarget(m_hdsp, Target);
	};

	public int MotionGetCurrentLaneIndex(int MotionTarget) {
		return dspMotionGetCurrentLaneIndex(m_hdsp, MotionTarget);
	};

	public int MotionGetResultType(int MotionTarget) {
		return dspMotionGetResultType(m_hdsp, MotionTarget);
	};

	public int MotionGetSpeedX(int MotionTarget) {
		return dspMotionGetSpeedX(m_hdsp, MotionTarget);
	};

	public int MotionGetSpeedY(int MotionTarget) {
		return dspMotionGetSpeedY(m_hdsp, MotionTarget);
	};

	public int MotionCfgLoad() {
		return dspMotionCfgLoad(m_hdsp);
	};

	public int MotionCfgSave() {
		return dspMotionCfgSave(m_hdsp);
	};

	public int RedLampDetectSetEnabled(int Params) {
		return dspRedLampDetectSetEnabled(m_hdsp, Params);
	};

	public int RedLampDetectGetNum() {
		return dspRedLampDetectGetNum(m_hdsp);
	};

	public int RedLampDetectSetNum(int Num) {
		return dspRedLampDetectSetNum(m_hdsp, Num);
	};

	public int RedLampDetectSetDisplayResultEnabled(int Params) {
		return dspRedLampDetectSetDisplayResultEnabled(m_hdsp, Params);
	};

	public int RedLampDetectSetDisplayRangeEnabled(int Params) {
		return dspRedLampDetectSetDisplayRangeEnabled(m_hdsp, Params);
	};

	public int RedLampDetectSetMinDots(int DotNum) {
		return dspRedLampDetectSetMinDots(m_hdsp, DotNum);
	};

	public int RedLampDetectGetMinDots() {
		return dspRedLampDetectGetMinDots(m_hdsp);
	};

	public int RedLampDetectSetRecogRange(int LampIndex, int Left, int Top, int Right, int Bottom) {
		return dspRedLampDetectSetRecogRange(m_hdsp, LampIndex, Left, Top, Right, Bottom);
	};

	public int RedLampDetectGetRecogRange(int LampIndex, ReturnIntValue pLeft, ReturnIntValue pTop,
			ReturnIntValue pRight, ReturnIntValue pBottom) {
		return dspRedLampDetectGetRecogRange(m_hdsp, LampIndex, pLeft, pTop, pRight, pBottom);
	};

	public int RedLampDetectGetLocate(int LampIndex, ReturnIntValue pLeft, ReturnIntValue pTop, ReturnIntValue pRight,
			ReturnIntValue pBottom) {
		return dspRedLampDetectGetLocate(m_hdsp, LampIndex, pLeft, pTop, pRight, pBottom);
	};

	public int RedLampDetectCfgLoad() {
		return dspRedLampDetectCfgLoad(m_hdsp);
	};

	public int RedLampDetectCfgSave() {
		return dspRedLampDetectCfgSave(m_hdsp);
	};

	private long m_hdsp;

	public long getM_hdsp() {
		return m_hdsp;
	}

	public void setM_hdsp(long m_hdsp) {
		this.m_hdsp = m_hdsp;
	}

}
