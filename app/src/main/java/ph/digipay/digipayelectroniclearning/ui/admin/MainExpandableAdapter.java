package ph.digipay.digipayelectroniclearning.ui.admin;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.models.VideoForm;

public class MainExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Module> moduleList;
    private List<String> subGroupList;
    private List<PDFForm> pdfFormList;
    private List<VideoForm> videoFormList;
    private List<Questionnaire> questionnaireList;

    public PublishSubject<PDFForm> pdfFormPublishSubject = PublishSubject.create();

    public PublishSubject<PDFForm> getPdfFormPublishSubject() {
        return pdfFormPublishSubject;
    }

    public PublishSubject<VideoForm> videoFormPublishSubject = PublishSubject.create();

    public PublishSubject<VideoForm> getVideoFormPublishSubject() {
        return videoFormPublishSubject;
    }

    public PublishSubject<Questionnaire> questionnairePublishSubject = PublishSubject.create();

    public PublishSubject<Questionnaire> getQuestionnairePublishSubject() {
        return questionnairePublishSubject;
    }

    public MainExpandableAdapter(Context context) {
        this.context = context;
        subGroupList = new LinkedList<>();
        subGroupList.add("PDF List");
        subGroupList.add("Video List");
        subGroupList.add("Questionnaire");
    }

    @Override
    public int getGroupCount() {
        return moduleList != null ? moduleList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return moduleList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subGroupList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Module module = (Module) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_expandable_list_main_group, null);
        }
        TextView groupTitleTv = convertView.findViewById(R.id.group_title_tv);
        groupTitleTv.setText(module.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(context);
        secondLevelELV.setAdapter(new SecondLevelAdapter(context, subGroupList, pdfFormList, videoFormList, questionnaireList));
        secondLevelELV.setGroupIndicator(null);
        return secondLevelELV;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
        notifyDataSetChanged();
    }

    public void setPdfFormList(List<PDFForm> pdfFormList) {
        this.pdfFormList = pdfFormList;
        notifyDataSetChanged();
    }

    public void setVideoFormList(List<VideoForm> videoFormList) {
        this.videoFormList = videoFormList;
        notifyDataSetChanged();
    }

    public void setQuestionnaireList(List<Questionnaire> questionnaireList) {
        this.questionnaireList = questionnaireList;
        notifyDataSetChanged();
    }

    class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    class SecondLevelAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<String> subGroupList;
        private List<PDFForm> pdfFormList;
        private List<VideoForm> videoFormList;
        private List<Questionnaire> questionnaireList;


        public SecondLevelAdapter(Context context,
                                  List<String> subGroupList,
                                  List<PDFForm> pdfFormList,
                                  List<VideoForm> videoFormList,
                                  List<Questionnaire> questionnaireList) {
            this.context = context;
            this.subGroupList = subGroupList;
            this.pdfFormList = pdfFormList;
            this.videoFormList = videoFormList;
            this.questionnaireList = questionnaireList;
        }


        @Override
        public int getGroupCount() {
            return subGroupList != null ? subGroupList.size() : 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition == 0) {
                return pdfFormList != null ? pdfFormList.size() : 0;
            }
            if (groupPosition == 1) {
                return videoFormList != null ? videoFormList.size() : 0;
            }
            if (groupPosition == 2) {
                return questionnaireList != null ? questionnaireList.size() : 0;
            }
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return subGroupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            if (groupPosition == 0) {
                return pdfFormList.get(childPosition);
            }
            if (groupPosition == 1) {
                return videoFormList.get(childPosition);
            }
            if (groupPosition == 2) {
                return questionnaireList.get(childPosition);
            }
            return 0;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_expandable_list_submain_group, null);
            }
            TextView subgroupTitleTv = convertView.findViewById(R.id.subgroup_title_tv);
            subgroupTitleTv.setText(subGroupList.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_expandable_list_child, null);
            }
            LinearLayout menuItem = convertView.findViewById(R.id.menu_item_ll);
            TextView childTitleTv = convertView.findViewById(R.id.child_content_tv);
            if (groupPosition == 0) {
                PDFForm pdfForm = (PDFForm) getChild(groupPosition, childPosition);
                childTitleTv.setText(pdfForm.getName());
                menuItem.setOnClickListener(v -> pdfFormPublishSubject.onNext(pdfForm));
            }
            if (groupPosition == 1) {
                VideoForm videoForm = (VideoForm) getChild(groupPosition, childPosition);
                childTitleTv.setText(videoForm.getName());
                menuItem.setOnClickListener(v -> videoFormPublishSubject.onNext(videoForm));
            }
            if (groupPosition == 2) {
                Questionnaire questionnaire = (Questionnaire) getChild(groupPosition, childPosition);
                childTitleTv.setText(questionnaire.getQuestion());
                menuItem.setOnClickListener(v -> questionnairePublishSubject.onNext(questionnaire));
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            super.registerDataSetObserver(observer);
        }
    }
}




