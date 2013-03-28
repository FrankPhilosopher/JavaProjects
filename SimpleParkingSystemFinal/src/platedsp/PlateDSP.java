// Don't edit this file !!!!!!!
// This is auto make.

package platedsp;

public abstract class PlateDSP extends base {

	// ��
	public boolean Open(String pGuiName) {
		ResetStatTargetSavedFlags();// �������б�־λ
		if (Create(pGuiName)) {// �����ɹ�����true
			SetSyncEventCallback(this);
			return true;
		}
		return false;
	}

	// �ر�
	public void Close() {
		Destroy();
	}

	public abstract void AfterDvrClosed();

	public abstract void AfterRecogFinished(int PlateNum);

	public abstract void AfterFilterStateChanged(int Event, int Target);

	public abstract void AfterImageSizeChanged(int Width, int Height);

	public abstract void AfterMotionStateChanged(int Event, int MotionTarget);

	public abstract void AfterRedLampStateChanged(int Color, int LampIndex);

	public abstract void AfterGetJpgSourceData(long pData, int Size);

	public abstract void AfterCompressedJpgData(long pData, int Size);

	// �ص�����
	public void Callback(int Type, int Data1, long Data2) {
		switch (Type) {
		case def.DSP_RECOG_EVENT:
			if (Data1 <= def.DSP_RECOG_HAS_RESULT) {
				AfterRecogFinished(Data1);
			} else {
				AfterFilterStateChanged(Data1, (int) Data2);
			}
			break;
		case def.DSP_FILE_EVENT:
			switch (Data1) {
			case def.DSP_DVR_CLOSED:
				AfterDvrClosed();
				break;
			}
			break;
		case def.DSP_IMAGE_SIZE_CHANGED_EVENT:
			AfterImageSizeChanged(Data1, (int) Data2);
			break;
		case def.DSP_MOTION_EVENT:
			AfterMotionStateChanged(Data1, (int) Data2);
			break;
		case def.DSP_RED_LAMP_EVENT:
			AfterRedLampStateChanged(Data1, (int) Data2);
			break;
		case def.DSP_JPG_SOURCE_EVENT:
			AfterGetJpgSourceData(Data2, Data1);
			break;
		case def.DSP_JPG_COMPRESSED_EVENT:
			AfterCompressedJpgData(Data2, Data1);
			break;
		}
	}

	// �õ�ĳ��״̬
	public boolean StatGetTargetSavedFlags(int TargetId) {
//        assert( TargetId >= 0 && TargetId < def.DSP_MAX_TARGET_NUM );
		return m_bSaved[TargetId];
	}

	// ����ĳ��״̬
	public void StatSetTargetSavedFlags(int TargetId, boolean bState) {
//        assert( TargetId >= 0 && TargetId < def.DSP_MAX_TARGET_NUM );
		m_bSaved[TargetId] = bState;
	}

	// �������б�־λ����ʼʱ����false
	public void ResetStatTargetSavedFlags() {
		for (int i = 0; i < def.DSP_MAX_TARGET_NUM; i++) {
			m_bSaved[i] = false;
		}
	}

	private boolean[] m_bSaved = new boolean[def.DSP_MAX_TARGET_NUM];
}
