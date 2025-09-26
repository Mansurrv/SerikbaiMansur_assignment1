import xml.etree.ElementTree as ET
import csv
import os

XML_FILE_PATH = 'target/surefire-reports/TEST-org.example.PointTest.xml'
CSV_FILE_PATH = 'surefire_Point_test_cases.csv'

def surefire_xml_to_csv(xml_path, csv_path):
    if not os.path.exists(xml_path):
        print(f"Ошибка: Файл не найден по пути: {xml_path}")
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
            print("Внимание: В XML-файле не найдено ни одного тега <testcase>.")
            return

        fieldnames = ['testsuite_name', 'testcase_name', 'class_name', 'time_seconds']

        with open(csv_path, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            
            writer.writeheader()
            
            writer.writerows(test_cases_data)

        print(f"Данные сохранены в файл: {csv_path}")

    except ET.ParseError as e:
        print(f"Ошибка парсинга XML: {e}")
    except Exception as e:
        print(f"Произошла непредвиденная ошибка: {e}")

if __name__ == "__main__":
    surefire_xml_to_csv(XML_FILE_PATH, CSV_FILE_PATH)