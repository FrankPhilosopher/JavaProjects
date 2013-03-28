// Don't edit this file !!!!!!!
// This is auto make.

package platedsp;

public class def
{
	public static final int DSP_MAX_TARGET_NUM = 4;
	public static final int DSP_MAX_RED_LAMP_NUM = 8;
	public static final int DSP_MAX_MOTION_LANE_NUM = 4;
	public static final int DSP_MAX_MOTION_TARGET_NUM = 8;

	//for dll version
	public static final int DSP_GET_VER_DLL_FULL_NAME = 0;
	public static final int DSP_GET_VER_DATA_TIME = 1;
	public static final int DSP_GET_VER_VERSION = 2;

	//for image display status
	public static final int DSP_DISPLAY_CAR_IMAGE = 0x1;
	public static final int DSP_DISPLAY_PLATE_IMAGE = 0x2;
	public static final int DSP_ERROR = -10000;

	//for recog type
	public static final int DSP_FAST_RECOG = 0x1;

	//for image op params
	public static final int DSP_HALFX = 0x1;
	public static final int DSP_HALFY = 0x2;
	public static final int DSP_WAIT_FINISHED = 0x4;
	public static final int DSP_NORMAL_VIEW = 0x8;
	public static final int DSP_NOT_CLEAR_BUFF = 0x10;
	public static final int DSP_ANGLE90_90 = 0x20;
	public static final int DSP_ANGLE90_270 = 0x40;
	public static final int DSP_BUFF_TO_BUFF = 0x80;
	public static final int DSP_AUTO_ZOOM_Y = 0x100;
	public static final int DSP_TRANS_TO_JPG = 0x200;

	//for stat type
	public static final int DSP_STAT_NONE = 0;
	public static final int DSP_STAT_OLD = 1;
	public static final int DSP_STAT_FILTER = 2;
	public static final int DSP_STAT_FILTER_SET_MAX_TARGET_NUM = 3;

	//for motion run mode
	public static final int DSP_MOTION_RUN_MODE_NONE = 0;
	public static final int DSP_MOTION_RUN_MODE_CROSSROADS = 1;
	public static final int DSP_MOTION_RUN_MODE_LANE = 2;
	public static final int DSP_MOTION_RUN_MODE_PARKING = 3;

	//for motion result
	public static final int DSP_MOTION_UNKNOW = 0;
	public static final int DSP_MOTION_UP_TO_DOWN = 0x1;
	public static final int DSP_MOTION_DOWN_TO_UP = 0x2;

	//for event
	public static final int DSP_RECOG_EVENT = 1;
	public static final int DSP_FILE_EVENT = 2;
	public static final int DSP_IMAGE_SIZE_CHANGED_EVENT = 3;
	public static final int DSP_MOTION_EVENT = 4;
	public static final int DSP_RED_LAMP_EVENT = 5;
	public static final int DSP_JPG_SOURCE_EVENT = 6;
	public static final int DSP_JPG_COMPRESSED_EVENT = 7;
	public static final int DSP_JPG_CLOSED = 1;
	public static final int DSP_AVI_CLOSED = 2;
	public static final int DSP_DVR_CLOSED = 3;

	//for AfterRecogFinished event
	public static final int DSP_MOTION_DETECT = -8000;
	public static final int DSP_FOUND_CHARS = -8001;
	public static final int DSP_JUST_CAPTURE = -1;
	public static final int DSP_RECOG_NO_RESULT = 0;
	public static final int DSP_RECOG_HAS_RESULT = 1;

	//for AfterFilterStateChanged event
	public static final int DSP_FILTER_OUT_VIEW = 10;
	public static final int DSP_FILTER_BEFORE_CLEAR = 11;
	public static final int DSP_FILTER_TIMER_OVER = 12;
	public static final int DSP_FILTER_NEW_PLATE = 13;
	public static final int DSP_FILTER_LIKE_PLATE = 14;
	public static final int DSP_FILTER_SAME_PLATE = 15;
	public static final int DSP_FILTER_FLASH = 20;

	//for AfterMotionStateChanged event
	public static final int DSP_MOTION_OUT_VIEW = 50;
	public static final int DSP_MOTION_BEFORE_CLEAR = 51;
	public static final int DSP_MOTION_TIMER_OVER = 52;
	public static final int DSP_MOTION_NEW = 53;
	public static final int DSP_MOTION_LIKE = 54;
	public static final int DSP_MOTION_LANE_CHANGED = 55;
	public static final int DSP_MOTION_CROSS_CENTER_Y = 56;

	//for video-device-index
	public static final int DSP_JUST_HI_VIDEO_INDEX = 1000;

	//for VideoGetOtherParams/VideoSetOtherParams
	public static final int DSP_VIDEO_CRYOSC = 0;
	public static final int DSP_VIDEO_BRIGHTNESS = 1;
	public static final int DSP_VIDEO_CONTRAST = 2;
	public static final int DSP_VIDEO_HUE = 3;
	public static final int DSP_VIDEO_SATURATION = 4;
	public static final int DSP_VIDEO_SHUTTER = 5;
	public static final int DSP_FLASH_SOURCE = 6;
	public static final int DSP_FLASH_WIDTH = 7;
	public static final int DSP_FLASH_DELAY = 8;
	public static final int DSP_VIDEO_RUN = 9;
	public static final int DSP_VIDEO_ADC_BIT = 10;
	public static final int DSP_VIDEO_DLL_HANDLE = 11;
	public static final int DSP_VIDEO_ANGLE90 = 12;
	public static final int DSP_VIDEO_DEVICE_HANDLE = 13;
	public static final int DSP_VIDEO_WHITE_BALANCE_ON_OFF = 14;
	public static final int DSP_VIDEO_JPG_QUALITY = 15;
	public static final int DSP_VIDEO_ANTI_FLICKER = 16;
	public static final int DSP_VIDEO_AUTO_GAIN_MIN = 17;
	public static final int DSP_VIDEO_AUTO_GAIN_MAX = 18;
	public static final int DSP_VIDEO_AUTO_SHUTTER_MIN = 19;
	public static final int DSP_VIDEO_AUTO_SHUTTER_MAX = 20;
	public static final int DSP_VIDEO_AUTO_SHUTTER = 21;
	public static final int DSP_VIDEO_AUTO_BRIGHTNESS = 22;
	public static final int DSP_VIDEO_WHITE_BALANCE_R = 23;
	public static final int DSP_VIDEO_WHITE_BALANCE_G = 24;
	public static final int DSP_VIDEO_WHITE_BALANCE_B = 25;
	public static final int DSP_VIDEO_CAMERA_WHITE_BALANCE_ON_OFF = 26;
	public static final int DSP_VIDEO_BAYER_TO_YUV = 27;
	public static final int DSP_VIDEO_TRIG_CAPTURE = 28;

	//for Video IO
	public static final int DSP_VIDEO_READ_CURRENT_FRAME = 0x10000000;
	public static final int DSP_VIDEO_IO_RS232_0 = 0x10;
	public static final int DSP_VIDEO_IO_RS232_1 = 0x11;
	public static final int DSP_VIDEO_IO_RS232_2 = 0x12;
	public static final int DSP_VIDEO_IO_RS232_3 = 0x13;
	public static final int DSP_VIDEO_IO_STATUS = 0x20;

	//for color
	public static final int CL_NOT_DEF = 0;
	public static final int CL_BLUE = 1;
	public static final int CL_BLACK = 2;
	public static final int CL_WHITE = 3;
	public static final int CL_YELLOW = 4;
	public static final int CL_RED = 5;
	public static final int CL_GREEN = 6;

	//for plate type ====================================================================
	//for function RecogCfgGetUseTemplate/RecogCfgSetUseTemplate

	//for plate type function flags
	public static final int PLATE_TYPE_ID = 0;
	public static final int PLATE_TYPE_ID_OR_DEFAULT = 0x40000000;
	public static final int PLATE_TYPE_ID_TAXI = 0x80000000;
	public static final int PLATE_TYPE_CONTAINER = 0xc0000000;

	//for plate type id flags
	public static final int PLATE_TYPE_ID_NORMAL = 0x1;
	public static final int PLATE_TYPE_ID_NORMAL5 = 0x2;
	public static final int PLATE_TYPE_ID_NORMAL6 = 0x4;
	public static final int PLATE_TYPE_ID_JC = 0x8;
	public static final int PLATE_TYPE_ID_WJ = 0x10;
	public static final int PLATE_TYPE_ID_ARMY = 0x20;

	public static final int PLATE_TYPE_ID_HONGKONG = 0x40;
	public static final int PLATE_TYPE_ID_MACAO = 0x80;
	public static final int PLATE_TYPE_ID_TAIWAN = 0x100;

	public static final int PLATE_TYPE_ID_BRAZIL = 0x40;
	public static final int PLATE_TYPE_ID_NIGERIA = 0x40;

	//for taxi type id
	public static final int TAXI_TYPE_ID_SHENZHEN = 0x1;

	//for plate container value
	public static final int PLATE_CONTAINER_ID_MAINLAND = 0;
	public static final int PLATE_CONTAINER_ID_HK = 1;
	public static final int PLATE_CONTAINER_ID_MACAO = 2;
	public static final int PLATE_CONTAINER_ID_TAIWAN = 3;
	public static final int PLATE_CONTAINER_ID_BRAZIL = 4;
	public static final int PLATE_CONTAINER_ID_NIGERIA = 5;

	//plate type end ====================================================================

	//for image format
	public static final int DSP_VIDEO_FORMAT_NONE = 0;
	public static final int DSP_VIDEO_FORMAT_RGB_8 = 8;
	public static final int DSP_VIDEO_FORMAT_RGB_X555 = 15;
	public static final int DSP_VIDEO_FORMAT_RGB_565 = 16;
	public static final int DSP_VIDEO_FORMAT_RGB_888 = 24;
	public static final int DSP_VIDEO_FORMAT_RGB_X888 = 32;
	public static final int DSP_VIDEO_FORMAT_UYVY = 0x59565955;
	public static final int DSP_VIDEO_FORMAT_YUY2 = 0x32595559;
	public static final int DSP_VIDEO_FORMAT_YUNV = 0x564e5559;
	public static final int DSP_VIDEO_FORMAT_V422 = 0x32323456;
	public static final int DSP_VIDEO_FORMAT_YUYV = 0x56595559;
	public static final int DSP_VIDEO_FORMAT_I420 = 0x30323449;
	public static final int DSP_VIDEO_FORMAT_IYUV = 0x56555949;
	public static final int DSP_VIDEO_FORMAT_YV12 = 0x32315659;
	public static final int DSP_VIDEO_FORMAT_Y42B = 0x42323459;
	public static final int DSP_VIDEO_FORMAT_JPEG = 0x4745504a;
	public static final int DSP_VIDEO_FORMAT_BAYER_GR = 0x52475942;
	public static final int DSP_VIDEO_FORMAT_BAYER_GB = 0x42475942;
	public static final int DSP_VIDEO_FORMAT_BAYER_RG = 0x47525942;
	public static final int DSP_VIDEO_FORMAT_BAYER_BG = 0x47425942;

}
