package platedsp;


public class MyDSP extends PlateDSP {

	public void AfterDvrClosed() {
	}

	public void AfterRecogFinished(int PlateNum) {
	}

	public void AfterImageSizeChanged(int Width, int Height) {
	}

	public void AfterMotionStateChanged(int Event, int MotionTarget) {
	}

	public void AfterRedLampStateChanged(int Color, int LampIndex) {
	}

	public void AfterGetJpgSourceData(long pData, int Size) {
	}

	public void AfterCompressedJpgData(long pData, int Size) {
		// byte[] JpgData = GetByteArray(pData,Size);
	}

	public void AfterFilterStateChanged(int Event, int Target) {
		int CarImageBufferBlock = Target;
		StatSetCurrentTarget(def.DSP_STAT_NONE, Target);
		switch (Event) {
		case def.DSP_FILTER_OUT_VIEW:
		case def.DSP_FILTER_BEFORE_CLEAR:
			SaveRecord(Target, CarImageBufferBlock);
			break;
		case def.DSP_FILTER_NEW_PLATE:
			BufferImageStream(CarImageBufferBlock);
			StatSetTargetSavedFlags(Target, false);
			break;
		case def.DSP_FILTER_LIKE_PLATE:
		case def.DSP_FILTER_SAME_PLATE:
			if (StatSetCurrentTarget(def.DSP_STAT_FILTER, Target) != 0) {
				if (ImageIsBest() != 0) {
					BufferImageStream(CarImageBufferBlock);
				}
				if (GetPlateCount() > 30) {
					SaveRecord(Target, CarImageBufferBlock);
				}
			}
			break;
		}
	}

	void SaveRecord(int Target, int CarImageBufferBlock) {
		if (StatGetTargetSavedFlags(Target) == true) {
			return;
		}
		StatSetTargetSavedFlags(Target, true);
		//
		if (StatSetCurrentTarget(def.DSP_STAT_FILTER, Target) != 0) {
			String PlateNumber = GetPlateNumber();
			BufferSave(CarImageBufferBlock, 0, "c:\\tmp\\" + PlateNumber + ".jpg", 0);
		}
	}

}
