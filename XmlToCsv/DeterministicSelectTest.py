import xml.etree.ElementTree as ET
import csv
import os

XML_FILE_PATH = '../target/surefire-reports/TEST-org.example.DeterministicSelectTest.xml'
CSV_FILE_PATH = 'surefire_DeterministicSelect_test_cases.csv'

def surefire_xml_to_csv(xml_path, csv_path):
    if not os.path.exists(xml_path):
        print(f"Error: {xml_path}")
        return

    try:
        tree = ET.parse(xml_path)
        root = tree.getroot()

        testsuite_name = root.get('name', 'N/A')
        
        test_cases_data = []
        
        for testcase in root.findall('testcase'):
            row = {
                'testsuite_name': testsuite_name,
                'testcase_name': testcase.get('name'),
                'class_name': testcase.get('classname'),
                'time_seconds': testcase.get('time')
            }
            test_cases_data.append(row)

        if not test_cases_data:
            return

        fieldnames = ['testsuite_name', 'testcase_name', 'class_name', 'time_seconds']

        with open(csv_path, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            
            writer.writeheader()
            
            writer.writerows(test_cases_data)

        print(f"Data saved in file: {csv_path}")

    except ET.ParseError as e:
        print(f"Parsing Error: {e}")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    surefire_xml_to_csv(XML_FILE_PATH, CSV_FILE_PATH)