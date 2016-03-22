package org.dynamicloud.wiztorage.processor;

import org.dynamicloud.api.BoundInstance;
import org.dynamicloud.api.annotation.Bind;

/**
 * Copyright (c) 2016 Dynamicloud
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * <code>UploadBean</code>
 * <p/>
 * This bean represents the fields in your Dynamicloud's model
 * <p/>
 * Available fields in your model:
 * name -> TextField (Required field) - Add an index in your model to speed up the queries with this field.
 * size -> Number (Required field)
 * type -> TextField (Required field)
 * checkSum -> TextField (Required field)
 * chunk -> TextArea (Required field)
 * sequence -> number (Required field)
 * checked -> Number (Optional in your model, but this field will be used to verify consistency of your files)
 * description -> TextArea (Optional)
 *
 * @author Eleazar Gomez
 */
public class UploadBean implements BoundInstance {
    private String name;
    private Long size;
    private String type;
    private String checkSum;
    private String chunk;
    private String description;
    private Integer checked;
    private Integer sequence;
    private Long count;
    private Long recordId;

    public String getName() {
        return name;
    }

    @Bind(field = "name")
    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    @Bind(field = "size")
    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    @Bind(field = "type")
    public void setType(String type) {
        this.type = type;
    }

    public String getCheckSum() {
        return checkSum;
    }

    @Bind(field = "checkSum")
    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public String getChunk() {
        return chunk;
    }

    @Bind(field = "chunk")
    public void setChunk(String chunk) {
        this.chunk = chunk;
    }

    public String getDescription() {
        return description;
    }

    @Bind(field = "description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getChecked() {
        return checked;
    }

    @Bind(field = "checked")
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Long getCount() {
        return count;
    }

    public Integer getSequence() {
        return sequence;
    }

    @Bind(field = "sequence")
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Bind(field = "count")
    public void setCount(Long count) {
        this.count = count;
    }

    public Number getRecordId() {
        return recordId;
    }

    @Bind(field = "rid")
    public void setRecordId(Number number) {
        this.recordId = number.longValue();
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", checkSum='" + checkSum + '\'' +
                ", chunk='" + chunk + '\'' +
                ", description='" + description + '\'' +
                ", checked=" + checked +
                ", count=" + count +
                ", recordId=" + recordId +
                '}';
    }
}